package net.legio.eve.engine.data.entity

import org.dizitart.no2.objects.Id

data class Race (
    @Id override val objectId: Long,
    val raceID: Int,
    val raceName: String,
    val description: String?,
    val iconID: Int,
    val shortDescription: String?
): ESDData