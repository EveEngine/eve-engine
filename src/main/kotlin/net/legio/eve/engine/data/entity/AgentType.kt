package net.legio.eve.engine.data.entity

import org.dizitart.no2.objects.Id

data class AgentType (
        @Id override val objectId: Long,
        val agentTypeID: Int,
        val agentType: String
): ESDData
