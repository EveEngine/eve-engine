package net.legio.eve.engine.data.entity

import org.dizitart.no2.objects.Id

data class Faction (
    @Id override var objectId: Long,
    val factionID: Int,
    val factionName: String,
    val description: String,
    val raceIDs: Int,
    val solarSystemID: Int,
    val corporationID: Int,
    val sizeFactor: Int,
    val stationCount: Int,
    val stationSystemCount: Int,
    val militiaCorporationID: Int,
    val iconID: Int
): ESDData