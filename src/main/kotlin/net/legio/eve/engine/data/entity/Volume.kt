package net.legio.eve.engine.data.entity

import org.dizitart.no2.objects.Id

data class Volume (
    @Id override val objectId: Long,
    val typeID: Int,
    val volume: Int
): ESDData
