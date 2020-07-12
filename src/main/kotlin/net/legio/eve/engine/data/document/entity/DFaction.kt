package net.legio.eve.engine.data.document.entity

import com.eve.engine.esi4k.model.Faction
import net.legio.eve.engine.data.document.DocumentItem
import org.dizitart.no2.NitriteId
import org.dizitart.no2.objects.Id

class DFaction(
        @Id override val objectId: NitriteId?,
        corporationId: Int?,
        description: String,
        factionId: Int,
        isUnique: Boolean?,
        militiaCorporationId: Int?,
        name: String,
        sizeFactor: Float,
        solarSystemId: Int?,
        stationCount: Int,
        stationSystemCount: Int
    ): Faction(
        corporationId,
        description,
        factionId,
        isUnique,
        militiaCorporationId,
        name,
        sizeFactor,
        solarSystemId,
        stationCount,
        stationSystemCount
    ), DocumentItem
