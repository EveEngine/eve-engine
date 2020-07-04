package net.legio.eve.engine.data.entity

import org.dizitart.no2.objects.Id

data class Mastery (
    @Id override val objectId: Long, 
    val typeID: Int,
    val masteryLevel: Int, 
    val certID: Int
): ESDData
