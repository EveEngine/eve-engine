package net.legio.eve.engine.data.entity

import org.dizitart.no2.objects.Id

data class CorpTrade (
    @Id override val objectId: Long, 
    val corporationID: Int,
    val typeID: Int
): ESDData