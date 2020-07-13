package net.legio.eve.engine.data.esi

import com.eve.engine.esi4k.ESIClient
import net.legio.eve.engine.core.EventBus
import net.legio.eve.engine.core.auth.BaseAuthManager
import net.legio.eve.engine.core.auth.SSOTokenSet
import net.legio.eve.engine.core.auth.TokenSetUpdatedEvent

class EsiDataService (private val authManager: BaseAuthManager, private val eventBus: EventBus? = null) {

    private var client = ESIClient()

    init {
        eventBus?.subscribe<TokenSetUpdatedEvent>{event -> onTokenSetUpdated(event.tokenSet)}
    }

    private fun onTokenSetUpdated(ssoTokenSet: SSOTokenSet?){
        client.accessToken = ssoTokenSet?.accessToken
    }

}