package net.legio.eve.engine.data.document.entity

import com.eve.engine.esi4k.model.Character
import com.eve.engine.esi4k.model.Gender
import net.legio.eve.engine.data.document.DocumentItem
import org.dizitart.no2.NitriteId
import org.dizitart.no2.objects.Id

class DCharacter(
    @Id override val objectId: NitriteId?,
    allianceId: Int?,
    ancestryId: Int?,
    birthday: String,
    bloodlineId: Int,
    corporationId: Int,
    description: String?,
    factionId: Int?,
    gender: Gender,
    name: String,
    raceId: Int,
    securityStatus: Float,
    title: String?
) : Character(
    allianceId,
    ancestryId,
    birthday,
    bloodlineId,
    corporationId,
    description,
    factionId,
    gender,
    name,
    raceId,
    securityStatus,
    title
), DocumentItem