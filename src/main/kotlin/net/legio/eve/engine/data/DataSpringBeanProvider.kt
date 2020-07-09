package net.legio.eve.engine.data

import net.legio.eve.engine.core.IWorkspace
import net.legio.eve.engine.core.auth.BaseAuthManager
import net.legio.eve.engine.data.document.DocumentESDRepository
import net.legio.eve.engine.data.document.IDocumentESDRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@Configuration
open class DataSpringBeanProvider {

    @Bean
    @Scope("singleton")
    open fun esdRepository(workspace: IWorkspace, authManager: BaseAuthManager): IDocumentESDRepository = DocumentESDRepository(workspace, authManager)

}