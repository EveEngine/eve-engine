package net.legio.eve.engine.core.auth

import net.legio.eve.engine.core.IWorkspace
import net.legio.eve.engine.toAbsoluteString
import java.nio.file.Path
import java.nio.file.Paths

class AuthManager(workspace: IWorkspace): BaseAuthManager(workspace) {

    private val credCachePath: Path = Paths.get(workspace.cacheDirectory().toAbsoluteString(), "sso.txt")

    override fun refresh() {
        TODO("Not yet implemented")
    }

}