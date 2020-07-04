package net.legio.eve.engine.data.entity

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.dizitart.no2.IndexType
import org.dizitart.no2.objects.Id
import org.dizitart.no2.objects.Index
import org.dizitart.no2.objects.Indices

@Indices (
    Index(value = "groupID", type = IndexType.Unique),
    Index(value = "categoryID")
)
data class InventoryGroup (
    @Id override val objectId: Long,
    val groupID: Int,
    val groupName: String,
    val categoryID: Int,
    val iconID: Int,
    val useBasePrice: Int,
    val anchored: Int,
    @JsonDeserialize(using = NumericBooleanDeserializer::class)
    val anchorable: Boolean,
    @JsonDeserialize(using = NumericBooleanDeserializer::class)
    val fittableNonSingleton: Boolean,
    @JsonDeserialize(using = NumericBooleanDeserializer::class)
    val published: Boolean
): ESDData
