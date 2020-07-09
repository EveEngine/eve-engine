package net.legio.eve.engine.data.document

import net.legio.eve.engine.core.IWorkspace
import net.legio.eve.engine.core.auth.BaseAuthManager
import net.legio.eve.engine.data.EsiDataRepository
import net.legio.eve.engine.toAbsoluteString
import org.dizitart.kno2.nitrite
import org.dizitart.no2.FindOptions
import org.dizitart.no2.Nitrite
import org.dizitart.no2.objects.ObjectFilter
import java.nio.file.Paths

class EsiDocumentDataRepository(workspace: IWorkspace): EsiDataRepository(workspace), IEsiDocumentDataRepository{

    private val database = nitrite {
        file = dbPath.toFile()
        autoCommit = true
        autoCommitBufferSize = 2048
        compress = true
        autoCompact = true
    }

    override fun isInitialized() {
        TODO("Not yet implemented")
    }

    override fun <T: DocumentItem> find(options: FindOptions): Array<T> {
        TODO("Not yet implemented")
    }

    override fun <T: DocumentItem> find(filter: ObjectFilter): Array<T> {
        TODO("Not yet implemented")
    }

    private inline fun <reified T: DocumentItem> insertBulk(data: Array<T>) {
        database.getRepository(T::class.javaObjectType).insert(data)
    }

}