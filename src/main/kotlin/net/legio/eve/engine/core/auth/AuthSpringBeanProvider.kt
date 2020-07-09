package net.legio.eve.engine.core.auth

import net.legio.eve.engine.core.IWorkspace
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@Configuration
open class AuthSpringBeanProvider {

    @Bean
    @Scope("singleton")
    open fun provideAuthManager(workspace: IWorkspace): BaseAuthManager = AuthManager(workspace)

}
