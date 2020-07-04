package net.legio.eve.engine.data.entity

import org.dizitart.no2.IndexType
import org.dizitart.no2.objects.Id
import org.dizitart.no2.objects.Index
import org.dizitart.no2.objects.Indices

@Indices (
    Index(value = "certID", type = IndexType.Unique)
)
data class Cert (
    @Id override val objectId: Long,
    val certID: Int,
    val description: String,
    val groupID: Int,
    val name: String
): ESDData
