package net.legio.eve.engine.core

import net.legio.eve.engine.SingletonBean
import net.legio.eve.engine.core.auth.EveSSOService
import org.springframework.context.annotation.Configuration

@Configuration
open class CoreSpringBeanProvider {

    @SingletonBean
    open fun provideEngineCoreService(): IEngineCoreService = EngineCoreService()

    @SingletonBean
    open fun eveSSOService(properties: EveEngineProperties): EveSSOService =
        EveSSOService(properties)

    @SingletonBean
    open fun workspace(): IWorkspace = Workspace()

}