package net.legio.eve.engine.data.entity

import org.dizitart.no2.IndexType
import org.dizitart.no2.objects.Id
import org.dizitart.no2.objects.Index
import org.dizitart.no2.objects.Indices

@Indices(
    Index(value = "ancestryID", type = IndexType.Unique)
)
data class Ancestry (
    @Id override val objectId: Long,
    val ancestryID: Int,
    val ancestryName: String,
    val bloodlineID: Int,
    val description: String?,
    val perception: Int,
    val willpower: Int,
    val charisma: Int,
    val memory: Int,
    val intelligence: Int,
    val iconID: Int,
    val shortDescription: String
): ESDData
