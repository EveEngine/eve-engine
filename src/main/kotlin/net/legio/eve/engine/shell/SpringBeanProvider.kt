package net.legio.eve.engine.shell

import net.legio.eve.engine.core.*
import net.legio.eve.engine.core.EngineCoreService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope


@Configuration
open class SpringBeanProvider {

    @Bean
    @Scope("singleton")
    open fun engineCoreService(): IEngineCoreService = EngineCoreService()

    @Bean
    @Scope("singleton")
    open fun eveSSOService(properties: EveEngineProperties): EveSSOService = EveSSOService(properties)

    @Bean
    @Scope("singleton")
    open fun workspace(): IWorkspace = Workspace()

}