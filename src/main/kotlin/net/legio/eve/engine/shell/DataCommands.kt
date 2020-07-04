package net.legio.eve.engine.shell

import net.legio.eve.engine.data.DocumentESDRepository
import net.legio.eve.engine.data.UpdateOutcome
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellCommandGroup
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import java.util.concurrent.atomic.AtomicBoolean

@ShellComponent
@ShellCommandGroup("data")
class DataCommands @Autowired constructor(val esdRepository: DocumentESDRepository) {

    @ShellMethod(value = "Initialize the ESD repository locally.", key = ["esd init"])
    fun _esdInit(): String {
        println("Initializing ESD repository...")
        esdRepository.initialize()
        esdRepository.metadata()?.let {
            println("Current MD5: ${it.currentMD5}")
            println("Database size: ${it.databaseSize}MB")
        }
        return "Repository initialized"
    }

    @ShellMethod(value = "Update the local ESD repository", key = ["esd update"])
    fun _esdUpdate(): String {
        val waiting = AtomicBoolean(true)
        var message = ""
        esdRepository.update {
            message = when(it){
                is UpdateOutcome.GoodUpdate -> "Good Update"
                is UpdateOutcome.BadUpdate -> it.message + it.exception?.message
            }
            waiting.set(false)
        }
        while(waiting.get()){
            Thread.sleep(250L)
        }
        return message
    }
}