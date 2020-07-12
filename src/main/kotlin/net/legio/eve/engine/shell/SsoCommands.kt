package net.legio.eve.engine.shell

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import net.legio.eve.engine.Failed
import net.legio.eve.engine.Success
import net.legio.eve.engine.core.auth.BaseAuthManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellCommandGroup
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import java.net.URL

@ShellComponent
@ShellCommandGroup("sso")
internal class SsoCommands @Autowired constructor(private val authManager: BaseAuthManager) {

    @ShellMethod(value = "Print the compiled sign in URL.", key = ["sso url"])
    fun _ssoUrl(): String {
        val result = authManager.compileSignInUrl()
        return when(result){
            is Success<URL> -> result.data.toString()
            is Failed -> "${result.error}: ${result.throwable?.message}"
        }
    }

    @ShellMethod(value = "Start the auth server to listen for sign in callback.", key = ["sso start"])
    fun _ssoStart(): String {
            authManager.startAuthListener()
        return "Auth server listening for sign in callback"
    }

    @ShellMethod(value = "Shutdown the auth server.", key = ["sso stop"])
    fun _ssoStop(): String{
        authManager.endAuthListener()
        return "Auth server shutdown"
    }

    @ShellMethod(value = "State of the local auth server.", key = ["sso status"])
    fun _ssoStatus(): String {
        return if(authManager.authListenerActive()) "Active" else "Shutdown"
    }

    @ShellMethod(value = "Display the current token set", key = ["sso tokens"])
    fun _ssoTokenSet(): String{
        return authManager.tokenSet?.let {
            """
                Token Type: ${it.tokenType}
                Access Token: ${it.accessToken}
                Refresh Token: ${it.refreshToken}
            """.trimIndent()
        }?: "No token set"
    }
}