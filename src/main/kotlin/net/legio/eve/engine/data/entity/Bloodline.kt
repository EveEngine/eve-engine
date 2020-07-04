package net.legio.eve.engine.data.entity

import org.dizitart.no2.objects.Id

data class Bloodline (
    @Id override val objectId: Long,
        val bloodlineID: Int,
        val bloodlineName: String,
        val raceID: Int,
        val description: String,
        val maleDescription: String,
        val femaleDescription: String,
        val shipTypeID: Int,
        val corporationID: Int,
        val perception: Int,
        val willpower: Int,
        val charisma: Int,
        val memory: Int,
        val intelligence: Int,
        val iconID: Int,
        val shortDescription: String,
        val shortMaleDescription: String,
        val shortFemaleDescription: String
): ESDData