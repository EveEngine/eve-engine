package net.legio.eve.engine.core

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

/**
 * Eve Engine core properties used by core services.
 */
@Configuration
@ConfigurationProperties(prefix = "eve.engine.core")
open class EveEngineProperties {
    /**\
     * The application client ID given when registering your application with developers.eveonline.com.
     */
    @Value("\${eve.engine.core.client-id}")
    var clientId: String? = null
        private set

    @Value("\${eve.engine.core.client-secret}")
    var clientSecret: String? = null
        private set

    /**
     * The list of ESI scopes used during user authentication with SSO. This list of scope should be comma separated
     * with no spaces. To get an [Array] of these scopes, use the [ssoScopesAsArray] property.
     */
    @Value("\${eve.engine.core.sso.scopes}")
    var ssoScopes: String? = null
        private set

    /**
     * Provides the [ssoScopes] as an [Array].
     */
    var scopesAsArray: Array<String>? = null
        get() {
            return ssoScopes?.split(" ")?.toTypedArray()
        }
        private set
}