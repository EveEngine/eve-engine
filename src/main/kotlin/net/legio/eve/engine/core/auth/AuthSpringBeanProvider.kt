package net.legio.eve.engine.core.auth

import net.legio.eve.engine.SingletonBean
import net.legio.eve.engine.core.IWorkspace
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@Configuration
open class AuthSpringBeanProvider {

    @SingletonBean
    open fun provideAuthManager(ssoService: EveSSOService, workspace: IWorkspace): BaseAuthManager = AuthManager(workspace, ssoService)

}
