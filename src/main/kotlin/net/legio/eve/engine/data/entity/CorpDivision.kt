package net.legio.eve.engine.data.entity

import org.dizitart.no2.IndexType
import org.dizitart.no2.objects.Id
import org.dizitart.no2.objects.Index
import org.dizitart.no2.objects.Indices

@Indices (
    Index(value = "divisionID", type = IndexType.Unique)
)
data class CorpDivision (
    @Id override val objectId: Long,
    val divisionID: Int,
    val divisionName: String,
    val description: String,
    val leaderType: String
): ESDData
