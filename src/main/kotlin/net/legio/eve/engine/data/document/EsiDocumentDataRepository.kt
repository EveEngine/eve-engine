package net.legio.eve.engine.data.document

import net.legio.eve.engine.core.IWorkspace
import net.legio.eve.engine.data.DataItem
import net.legio.eve.engine.toAbsoluteString
import org.dizitart.kno2.nitrite
import org.dizitart.no2.FindOptions
import org.dizitart.no2.objects.ObjectFilter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.file.Paths
import kotlin.reflect.KClass

class EsiDocumentDataRepository(workspace: IWorkspace): IEsiDocumentDataRepository{

    private val dbPath = Paths.get(workspace.dataDirectory().toAbsoluteString(), "esi.db")

    private val database = nitrite {
        file = dbPath.toFile()
        autoCommit = true
        autoCommitBufferSize = 2048
        compress = true
        autoCompact = true
    }

    override fun isInitialized(): Boolean {
        getMetadata().let {md ->
            if(md.initializedOn == null){
                return false
            }
        }
        return true
    }

    fun initialize(){
        if(isInitialized()) return

    }

    override fun clear() {
        LOG.trace("Clearing ESI document repository")
        getMetadata().let { md ->
            md.repoRegistry?.also { rr ->
                rr.repoKeys().forEach { key ->
                    database.getRepository(rr.klassForKey(key)?.javaObjectType)?.drop()
                    LOG.trace(" Cleared $key")
                }
            }
        }

        LOG.trace("Repository cleared")
    }

    private fun updateMetadata(metadata: RepoMetadata){
        val repo = database.getRepository(RepoMetadata::class.java)
        if(metadata.id == null){
            repo.insert(metadata)
        }else{
            repo.update(metadata)
        }
    }

    private fun getMetadata(): RepoMetadata {
        val repo = database.getRepository(RepoMetadata::class.java)
        return repo.find().firstOrNull()?:RepoMetadata.createDefault()
    }

    override fun <T : DataItem> registerType(klass: KClass<T>) {
        getMetadata().repoRegistry.registerKey(klass.simpleName!!, klass)
    }

    override fun <T: DataItem> find(options: FindOptions, klass: KClass<T>): List<T> {
        val results = database.getRepository(klass.javaObjectType).find(options)
        return results.toList()
    }

    override fun <T: DataItem> find(filter: ObjectFilter, klass: KClass<T>): List<T> {
        val results = database.getRepository(klass.javaObjectType).find(filter)
        return results.toList()
    }

    override fun <T:DataItem> insertBulk(items: Array<T>, klass: KClass<T>){
        database.getRepository(klass.javaObjectType).insert(items)
    }

    override fun <T : DataItem> insert(item: T, klass: KClass<T>) {
        database.getRepository(klass.javaObjectType).insert(item)
    }

    companion object {
        @JvmStatic private val LOG: Logger = LoggerFactory.getLogger(EsiDocumentDataRepository::class.java)
    }

}



