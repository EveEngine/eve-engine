package net.legio.eve.engine.data

import net.legio.eve.engine.SingletonBean
import net.legio.eve.engine.core.IWorkspace
import net.legio.eve.engine.core.Workspace
import net.legio.eve.engine.core.auth.BaseAuthManager
import net.legio.eve.engine.core.auth.EveSSOService
import net.legio.eve.engine.data.esi.EsiDataService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@Configuration
open class DataSpringBeanProvider {

    @SingletonBean
    open fun provideEsiDataService(authManager: BaseAuthManager) = EsiDataService(authManager)
}