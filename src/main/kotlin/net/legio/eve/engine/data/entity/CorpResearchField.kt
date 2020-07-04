package net.legio.eve.engine.data.entity

import org.dizitart.no2.objects.Id

data class CorpResearchField (
    @Id override val objectId: Long,
    val skillID: Int,
    val corporationID: Int
): ESDData
