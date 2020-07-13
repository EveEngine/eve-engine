package net.legio.eve.engine.data.document

import net.legio.eve.engine.data.DataItem
import org.dizitart.no2.objects.Id
import java.time.Instant

internal class RepoMetadata(
    @Id override val id: Long?,
    var initializedOn: Instant?,
    var dbSize: Double,
    var repoRegistry: RepoRegistry
): DataItem {

    companion object {
        @JvmStatic
        fun createDefault() = RepoMetadata(null, null, 0.0, RepoRegistry())
    }
}