package net.legio.eve.engine.shell

import net.legio.eve.engine.core.IEngineCoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellCommandGroup
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
@ShellCommandGroup("core")
internal class CoreCommands @Autowired constructor(val coreService: IEngineCoreService) {

    @ShellMethod(value = "Check connectivity to the network", key = ["core network status"])
    fun _coreNetworkStatus(): String{
        return coreService.hasInternetConnection().toString()
    }

    companion object {
        const val GROUP = "core"
    }

}