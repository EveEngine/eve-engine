package net.legio.eve.engine.core

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@Configuration
open class CoreSpringBeanProvider {

    @Bean
    @Scope("singleton")
    open fun provideEngineCoreService(): IEngineCoreService = EngineCoreService()

    @Bean
    @Scope("singleton")
    open fun eveSSOService(properties: EveEngineProperties): EveSSOService = EveSSOService(properties)

    @Bean
    @Scope("singleton")
    open fun workspace(): IWorkspace = Workspace()

}