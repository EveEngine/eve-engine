package net.legio.eve.engine.core

import net.legio.eve.engine.SingletonBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@Configuration
open class CoreSpringBeanProvider {

    @SingletonBean
    open fun provideEngineCoreService(): IEngineCoreService = EngineCoreService()

    @SingletonBean
    open fun eveSSOService(properties: EveEngineProperties): EveSSOService = EveSSOService(properties)

    @SingletonBean
    open fun workspace(): IWorkspace = Workspace()

}