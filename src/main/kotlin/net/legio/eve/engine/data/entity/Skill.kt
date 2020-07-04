package net.legio.eve.engine.data.entity

import org.dizitart.no2.objects.Id

data class Skill (
    @Id override val objectId: Long, 
    val certID: Int,
    val skillID: Int,
    val certLevelInt: Int,
    val skillLevel: Int,
    val certLevelText: String
): ESDData
