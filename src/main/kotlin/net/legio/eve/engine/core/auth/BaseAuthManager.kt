package net.legio.eve.engine.core.auth

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.*
import net.legio.eve.engine.*
import net.legio.eve.engine.core.IWorkspace
import org.apache.http.NameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.message.BasicNameValuePair
import org.slf4j.LoggerFactory
import java.net.URL
import java.nio.charset.Charset

abstract class BaseAuthManager(private val ssoService: EveSSOService) {
    private val scope = GlobalScope
    private val refreshJob = Job()

    var tokenSet: SSOTokenSet? = null
        set(value){
            field = value
            resetRefresh(field != null)
        }

    protected fun startup(){
        val result = loadSSOTokenSet()
        if(result is Success) {
            tokenSet = result.data!!
            startRefreshJob()
        }
        ssoService.onSSOSuccess = { tokenSet ->
            this.tokenSet = tokenSet
            saveSSOTokenSet(tokenSet!!)
        }
    }

    protected abstract fun loadSSOTokenSet(): Result<SSOTokenSet>

    protected abstract fun saveSSOTokenSet(tokenSet: SSOTokenSet): Result<Unit>

    /**
     * Cancels the current refreshJob if it is running.
     */
    private fun resetRefresh(restart: Boolean = true){
        scope.launch {
            if(refreshJob.isActive) refreshJob.cancelAndJoin()
            if(restart) startRefreshJob()
        }
    }

    private fun startRefreshJob(){
        if(refreshJob.isActive){
            resetRefresh(true)
        }
        scope.launch(Dispatchers.Default + refreshJob) {
            tokenSet?.let {
                delay((it.expiresIn - 10L) * 1000)
                var tries = 0
                reqLoop@while(tries < 5) {
                    tries++
                    var result = refreshToken()
                    when (result) {
                        is Success<SSOTokenSet> -> {
                            tokenSet = result.data
                            saveSSOTokenSet(tokenSet!!)
                            break@reqLoop
                        }
                        is Failed -> {
                            LOG.error("${result.error}: Try $tries")
                        }
                    }
                }
            }
        }
    }

    private fun refreshToken(): Result<SSOTokenSet>{
        val request = HttpPost(refreshUrl).apply {
            entity = UrlEncodedFormEntity(arrayListOf<NameValuePair>(
                    BasicNameValuePair("grant_type", "refresh_token"),
                    BasicNameValuePair("refresh_token", tokenSet!!.refreshToken),
                    BasicNameValuePair("client_id", ssoService.properties.clientId),
                    BasicNameValuePair("scope", ssoService.properties.ssoScopes)
                )
            )
            addHeader("Content-Type", ContentType.APPLICATION_FORM_URLENCODED.mimeType)
            addHeader("Host","login.eveonline.com")
        }
        val client = HttpClientBuilder.create().build()
        val response = client.execute(request)
        val respCode = response.statusLine.statusCode
        return if(respCode == 200) {
            val content = response.entity.content.contentAsString(Charset.defaultCharset())
            val respTokenSet: SSOTokenSet = jacksonObjectMapper().readValue(content!!)
            success(respTokenSet)
        }else{
            val error = "Failed to refresh access token: ${response.statusLine.reasonPhrase}"
            failed(error, null)
        }
    }

    fun startAuthListener(){
        if(ssoService.isRunning()){
            ssoService.stop()
        }
        ssoService.start()
    }

    fun endAuthListener(){
        if(ssoService.isRunning()){
            ssoService.stop()
        }
    }

    fun authListenerActive(): Boolean {
        return ssoService.isRunning()
    }

    fun compileSignInUrl(): Result<URL>{
        return try{
            success(URL(ssoService.signInUrl()))
        }catch (e: Exception){
            failed("Unable to build sign in URL.", e)
        }
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(BaseAuthManager::class.java)
        private const val refreshUrl = "https://login.eveonline.com/v2/oauth/token"

    }

}