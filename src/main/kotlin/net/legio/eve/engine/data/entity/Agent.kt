package net.legio.eve.engine.data.entity

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.dizitart.no2.objects.Id

data class Agent (
    @Id override val objectId: Long,
    val agentID: Int,
    val  divisionID: Int,
    val corporationID: Int,
    val locationID: Int,
    val level: Int,
    val quality: Int,
    val agentTypeID: Int,
    @JsonDeserialize(using = NumericBooleanDeserializer::class)
    val isLocator: Boolean
): ESDData
