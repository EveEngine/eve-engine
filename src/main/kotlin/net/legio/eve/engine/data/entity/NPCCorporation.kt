package net.legio.eve.engine.data.entity

import org.dizitart.no2.objects.Id

data class NPCCorporation (
    @Id override val objectId: Long,
    var corporationID: Int,
    var size: String,
    var extent: String,
    var solarSystemID: Int,
    var investorID1: Int,
    var investorShares1: Int,
    var investorID2: Int,
    var investorShares2: Int,
    var investorID3: Int,
    var investorShares3: Int,
    var investorID4: Int,
    var investorShares4: Int,
    var friendID: Int,
    var enemyID: Int,
    var publicShares: Int,
    var initialPrice: Double,
    var minSecurity: Double,
    var scattered: Int,
    var fringe: Int,
    var corridor: Int,
    var hub: Int,
    var border: Int,
    var factionID: Int,
    var sizeFactor: Double,
    var stationCount: Int,
    var stationSystemCount: Int,
    var description: String,
    var iconID: Int
): ESDData
