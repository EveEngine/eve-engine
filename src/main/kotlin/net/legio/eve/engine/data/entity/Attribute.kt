package net.legio.eve.engine.data.entity

import org.dizitart.no2.IndexType
import org.dizitart.no2.objects.Id
import org.dizitart.no2.objects.Index
import org.dizitart.no2.objects.Indices

@Indices(
    Index(value = "attributeID", type = IndexType.Unique)
)
data class Attribute(
        @Id override val objectId: Long, 
        val attributeID: Int,
        val attributeName: String,
        val description: String,
        val iconID: Int,
        val shortDescription: String,
        val notes: String
): ESDData
