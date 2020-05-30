package net.legio.eve.engine.shell

import net.legio.eve.engine.core.IWorkspace
import net.legio.eve.engine.core.Workspace
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellCommandGroup
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import java.io.IOException
import java.lang.StringBuilder
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes

@ShellComponent
@ShellCommandGroup("workspace")
internal class WorkspaceCommands @Autowired constructor(
        private val workspace: IWorkspace
) {

    @ShellMethod(value = "Check that the workspace exists.", key = ["workspace exists"])
    fun _workspaceExists(): Boolean {
        return Files.exists(this.workspace.rootDirectory())
    }

    @ShellMethod(value = "Get the workspace location.", key = ["workspace path"])
    fun _workspacePath(): String {
        return if(!Files.exists(this.workspace.rootDirectory())){
            "Error: Workspace does not exist"
        }else {
            this.workspace.rootDirectory().toAbsolutePath().toString()
        }
    }

    @ShellMethod(value = "Print the workspace file tree.", key = ["workspace display"])
    fun _workspaceDisplay(): String {
        val strBuilder = StringBuilder()
        var level = 0;
        val visitor = object : SimpleFileVisitor<Path>() {
            override fun preVisitDirectory(dir: Path?, attrs: BasicFileAttributes?): FileVisitResult {
                return super.preVisitDirectory(dir, attrs).apply {
                    if(this == FileVisitResult.CONTINUE){
                        repeat(level) {strBuilder.append("|  ")}
                        strBuilder.append("+--")
                        strBuilder.append(dir?.fileName)
                                .append("/\n")
                        level++
                    }
                }
            }

            override fun postVisitDirectory(dir: Path?, exc: IOException?): FileVisitResult {
                return super.postVisitDirectory(dir, exc).apply {
                    level--
                }
            }

            override fun visitFile(file: Path?, attrs: BasicFileAttributes?): FileVisitResult {
                return super.visitFile(file, attrs).apply {
                    repeat(level) { strBuilder.append("|  ") }
                    strBuilder.append("---")
                            .append(file?.fileName)
                            .append("\n")
                }
            }
        }
        Files.walkFileTree(this.workspace.rootDirectory(), visitor)
        return strBuilder.toString()
    }

}