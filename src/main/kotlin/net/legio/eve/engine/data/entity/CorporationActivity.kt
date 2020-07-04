package net.legio.eve.engine.data.entity

import org.dizitart.no2.objects.Id

data class CorporationActivity (
    @Id override val objectId: Long,
    val activityID: Int,
    val activityName: String,
    val description: String
): ESDData