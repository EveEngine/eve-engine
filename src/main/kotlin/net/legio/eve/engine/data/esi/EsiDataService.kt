package net.legio.eve.engine.data.esi

import com.eve.engine.esi4k.ESIClient
import net.legio.eve.engine.core.auth.BaseAuthManager

class EsiDataService (val authManager: BaseAuthManager) {

    private val client= ESIClient

}