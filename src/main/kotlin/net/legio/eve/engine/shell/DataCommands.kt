package net.legio.eve.engine.shell

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellCommandGroup
import org.springframework.shell.standard.ShellComponent

@ShellComponent
@ShellCommandGroup("data")
class DataCommands @Autowired constructor() {

}