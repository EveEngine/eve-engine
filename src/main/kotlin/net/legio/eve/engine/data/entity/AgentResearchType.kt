package net.legio.eve.engine.data.entity

import org.dizitart.no2.objects.Id

data class AgentResearchType(
    @Id override val objectId: Long,
    val agentID: Int,
    val typeID: Int
): ESDData
