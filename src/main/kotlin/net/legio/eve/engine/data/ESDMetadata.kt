package net.legio.eve.engine.data

import org.dizitart.no2.objects.Id
import java.time.LocalDateTime

data class ESDMetadata (
    @Id var objectId: Long? = null,
    var currentMD5: String? = null,
    var lastUpdated: LocalDateTime? = null,
    var databaseSize: Double? = null
)