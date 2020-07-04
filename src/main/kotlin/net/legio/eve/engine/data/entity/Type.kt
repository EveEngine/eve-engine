package net.legio.eve.engine.data.entity

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.dizitart.no2.IndexType
import org.dizitart.no2.objects.Id
import org.dizitart.no2.objects.Index
import org.dizitart.no2.objects.Indices

@Indices (
    Index(value ="typeID", type = IndexType.NonUnique)
)
data class Type (
    @Id override val objectId: Long,
    val typeID: Int,
    val groupID: Int,
    val typeName: String,
    val description: String?,
    val mass: Double,
    val volume: Long,
    val capacity: Int,
    val portionSize: Int,
    val raceID: Int,
    val basePrice: Double,
    @JsonDeserialize(using = NumericBooleanDeserializer::class)
    val published: Boolean,
    val marketGroupID: Int,
    val iconID: Int,
    val soundID: Int,
    val graphicID: Int
): ESDData
