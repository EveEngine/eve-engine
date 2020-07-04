package net.legio.eve.engine.data.entity

import org.dizitart.no2.IndexType
import org.dizitart.no2.objects.Id
import org.dizitart.no2.objects.Index
import org.dizitart.no2.objects.Indices

@Indices (
    Index(value = "itemID", type = IndexType.Unique)
)
data class Item (
    @Id override val objectId: Long,
    val itemID: Int,
    val typeID: Int,
    val ownerID: Int,
    val locationID: Int,
    val flagID: Int,
    val quantity: Long
): ESDData
