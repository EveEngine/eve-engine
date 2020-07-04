package net.legio.eve.engine.data.entity

import org.dizitart.no2.IndexType
import org.dizitart.no2.objects.Id
import org.dizitart.no2.objects.Index
import org.dizitart.no2.objects.Indices

@Indices (
    Index(value = "itemID", type = IndexType.Unique),
    Index(value = "itemName", type = IndexType.Fulltext)
)
class InventoryName (
    @Id override val objectId: Long,
    val itemID: Int,
    val itemName: String
): ESDData
