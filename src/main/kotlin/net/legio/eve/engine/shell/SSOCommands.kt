package net.legio.eve.engine.shell

import net.legio.eve.engine.core.EveSSOService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellCommandGroup
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
@ShellCommandGroup("sso")
internal class SSOCommands @Autowired constructor(val ssoService: EveSSOService) {

    @ShellMethod(value = "View the currently configured scopes", key = ["sso scopes"])
    fun _sscoScopes(): String {
        val scopes = ssoService.properties.ssoScopes
        if(scopes.isNullOrEmpty()){
            return "[no scopes configured]"
        }
        return scopes
    }

    @ShellMethod(value = "Get the current status of the local callback server", key = ["sso server status"])
    fun _ssoServerStatus(): String {
        return if(ssoService.isRunning()) "[running]" else "[down]"
    }

    @ShellMethod(value = "Start up the local callback server", key = ["sso server start"])
    fun _ssoServerStart(): String {
        if(ssoService.isRunning()){
            return "Server is already running"
        }
        ssoService.start()
        Thread.sleep(250L)
        if(ssoService.isRunning()){
            return "Server started"
        }
        return "Server failed to start"
    }

    @ShellMethod(value = "Stop the local callback server", key = ["sso server stop"])
    fun _ssoServerStop(): String {
        if(ssoService.isRunning()){
            ssoService.stop()
            Thread.sleep(250L)
            if(ssoService.isRunning()){
                return "Server failed to stop"
            }
            return "Server stopped"
        }else{
            return "Server is not running"
        }
    }

    @ShellMethod(value = "Get the generated ESI SSO link", key = ["sso link"])
    fun _ssoLink(): String {
        return ssoService.signInUrl()?:"Failed to create link"
    }
}