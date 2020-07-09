package net.legio.eve.engine.data.document.entity

import com.eve.engine.esi4k.model.Bloodline
import net.legio.eve.engine.data.document.DocumentItem

class DBloodline(
    override val objectId: Long,
    bloodlineId: Int,
    charisma: Int,
    corporationId: Int,
    description: String,
    intelligence: Int,
    memory: Int,
    name: String,
    perception: Int,
    raceId: Int,
    shipTypeId: Int,
    willpower: Int
): Bloodline (
    bloodlineId,
    charisma,
    corporationId,
    description,
    intelligence,
    memory,
    name,
    perception,
    raceId,
    shipTypeId,
    willpower
), DocumentItem
