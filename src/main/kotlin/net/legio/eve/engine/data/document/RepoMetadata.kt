package net.legio.eve.engine.data.document

import org.dizitart.no2.NitriteId
import org.dizitart.no2.objects.Id
import java.time.Instant

internal class RepoMetadata(
    @Id override val objectId: NitriteId?,
    var initializedOn: Instant?,
    var dbSize: Double,
    var repoRegistry: RepoRegistry
): DocumentItem {

    companion object {
        @JvmStatic
        fun createDefault() = RepoMetadata(null, null, 0.0, RepoRegistry())
    }
}