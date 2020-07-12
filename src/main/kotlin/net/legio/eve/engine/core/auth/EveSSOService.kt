package net.legio.eve.engine.core.auth

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.sun.net.httpserver.HttpContext
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import kotlinx.coroutines.Job
import net.legio.eve.engine.core.EveEngineProperties
import org.apache.http.HttpHeaders
import org.apache.http.NameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.message.BasicNameValuePair
import java.io.IOException
import java.lang.StringBuilder
import java.net.InetSocketAddress
import java.net.URLEncoder
import java.security.MessageDigest
import java.security.SecureRandom
import java.time.Instant
import java.util.*

class EveSSOService (val properties: EveEngineProperties){
    private var server: HttpServer? = null
    private var context: HttpContext? = null
    private var isRunning: Boolean = false
    private var job: Job? = null
    private val authCredsEncoded: ByteArray
    private var state: String? = null
    var onSSOSuccess: ((SSOTokenSet) -> Unit)? = null
    val challenge: String
    private val challengeEncoded: String

    init {
        val authCredentials = "${properties.clientId}:${properties.clientSecret}"
        authCredsEncoded = Base64.getUrlEncoder().encode(authCredentials.toByteArray())
        val bytes = Base64.getUrlEncoder().encode(ByteArray(32).apply {
            SecureRandom.getInstanceStrong().nextBytes(this)
        })
        challenge = bytes.toString(Charsets.UTF_8)
        val digest = MessageDigest.getInstance("SHA-256")
        digest.update(bytes)
        val digestBytes = digest.digest()
        challengeEncoded = Base64.getUrlEncoder().encode(digestBytes).toString(Charsets.UTF_8).replace("=","")
    }

    fun signInUrl(): String? {
        state = Instant.now().toEpochMilli().toString()
        return StringBuilder("https://login.eveonline.com/v2/oauth/authorize?")
            .append("response_type=code&")
            .append("redirect_uri=${URLEncoder.encode("http://localhost:12225/esi/auth/", Charsets.UTF_8)}&")
            .append("client_id=${properties.clientId}&")
            .append("scope=${properties.ssoScopes?.replace(" ","%20")}&")
            .append("code_challenge=$challengeEncoded&")
            .append("code_challenge_method=S256&")
            .append("state=$state")
            .toString()
    }

    fun start() {
        if(this.isRunning){
            return
        }
        server = HttpServer.create(InetSocketAddress(12225), 0);
        context = server?.createContext("/esi/auth")
        context?.handler = object : HttpHandler {
            override fun handle(exchange: HttpExchange?) {
                if (exchange == null) {
                    return
                }

                // Get the response from ESI SSO
                val queryStr = exchange.requestURI.query
                val queryMap: MutableMap<String, String> = HashMap()
                queryStr.split("&").forEach { item ->
                    item.indexOfFirst { c -> c == '=' }.let {
                        queryMap[item.substring(0, it)] = item.substring(it + 1)
                    }
                }

                val request = HttpPost("https://login.eveonline.com/v2/oauth/token")
                val list = ArrayList<NameValuePair>()
                list.add(BasicNameValuePair("grant_type", "authorization_code"))
                list.add(BasicNameValuePair("code", queryMap["code"]!!))
                list.add(BasicNameValuePair("client_id",properties.clientId))
                list.add(BasicNameValuePair("code_verifier",challenge))
                request.entity = UrlEncodedFormEntity(list)
                request.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.mimeType)
                request.setHeader(HttpHeaders.HOST, "login.eveonline.com")

                val client = HttpClientBuilder.create().build()
                val response = client.execute(request)
                val respCode = response.statusLine.statusCode

                if (respCode != 200) {
                    var error: String? = null
                    when (respCode) {
                        401 -> error = "ESI SSO returned 'UNAUTHORIZED'"
                        500 -> error = "ESI SSO server experienced an error"
                        400 -> error = "Bad request sent to ESI SSO"
                    }
                    println("$error ${response.statusLine.reasonPhrase}")
                } else {
                    val content: String
                    try {
                        content = response.entity.content.let { stream -> String(stream.readBytes()) }
                    } catch (e: IOException) {
                        e.printStackTrace()
                        return
                    }

                    val tokenSet: SSOTokenSet = ObjectMapper().readValue(content)
                    onSSOSuccess?.invoke(tokenSet)
                }
            }
        }
        server?.start()
        this.isRunning = true
    }

    fun stop(){
        server?.stop(0)
        server?.removeContext(context)
        this.isRunning = false
    }

    fun isRunning(): Boolean = this.isRunning

}