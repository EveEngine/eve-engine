package net.legio.eve.engine.core.auth

import net.legio.eve.engine.core.IWorkspace

abstract class BaseAuthManager(private val workspace: IWorkspace) {

    var tokenSet: SSOTokenSet? = null

    protected abstract fun refresh()


}