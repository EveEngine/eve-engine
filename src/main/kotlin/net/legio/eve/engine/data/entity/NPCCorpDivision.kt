package net.legio.eve.engine.data.entity

import org.dizitart.no2.objects.Id

data class NPCCorpDivision (
    @Id override val objectId: Long, 
    val corporationID: Int,
    val divisionID: Int,
    val size: Int
): ESDData
