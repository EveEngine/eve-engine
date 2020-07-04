package net.legio.eve.engine.data.entity

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.dizitart.no2.IndexType
import org.dizitart.no2.objects.Id
import org.dizitart.no2.objects.Index
import org.dizitart.no2.objects.Indices

@Indices(
    Index(value = "categoryID", type = IndexType.Unique)
)
data class Category (
    @Id override val objectId: Long,
    val categoryID: Int,
    val categoryName: String,
    val iconID: Int,
    @JsonDeserialize(using = NumericBooleanDeserializer::class)
    val published: Boolean
): ESDData
