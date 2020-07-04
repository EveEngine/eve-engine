package net.legio.eve.engine.data

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import net.legio.eve.engine.Outcome
import net.legio.eve.engine.core.IWorkspace
import net.legio.eve.engine.data.ESDRepository.Companion.SDE_MD5_ADDR
import net.legio.eve.engine.data.UpToDateCheck.*
import net.legio.eve.engine.data.document.IDocumentESDRepository
import net.legio.eve.engine.data.entity.ESDData
import org.dizitart.kno2.nitrite
import org.dizitart.no2.FindOptions
import org.dizitart.no2.Nitrite
import org.dizitart.no2.objects.ObjectFilter
import java.io.IOException
import java.lang.IllegalStateException
import java.net.URL
import java.nio.channels.Channels
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class DocumentESDRepository(val workspace: IWorkspace): IDocumentESDRepository{

    override val databasePath: Path
    private var database: Nitrite

    init {
        if(!Files.exists(workspace.dataDirectory())){
            throw IllegalStateException("Workspace data directory does not exist.")
        }
        databasePath = Paths.get(workspace.dataDirectory().toAbsolutePath().toString(), "esd.db")
        database = nitrite {
            readOnly
            file = databasePath.toFile()
        }
    }

    /**
     * Returns true of metadata exists.
     */
    override fun isInitialized(): Boolean {
        return metadata() == null
    }

    fun initialize() {

    }

    override fun <T : ESDData> find(filter: ObjectFilter): Array<T> {
        TODO("Not yet implemented")
    }

    override fun <T : ESDData> find(options: FindOptions): Array<T> {
        TODO("Not yet implemented")
    }

    override fun isUpToDate(): UpToDateCheck {
        if(metadata()?.currentMD5 == null){
            return OutOfDate
        }
        return try {
            val remoteMD5 = with(URL(ESDRepository.SDE_MD5_ADDR).openConnection()) {
                setRequestProperty("User-Agent", "EveEngine")
                connect()
                getInputStream().readAllBytes().toString(Charsets.UTF_8)
            }
            if (metadata()?.currentMD5 == remoteMD5) UpToDate else OutOfDate
        }catch (e : Exception){
            BadCheck("Unable to connect to host $SDE_MD5_ADDR", e)
        }
    }

    override fun update(callback: (UpdateOutcome) -> Unit) {
        if(isUpToDate() !is UpToDate){
            synchronized(database){
                writeDatabase()
                val esdRawDir = Files.createTempDirectory(workspace.dataDirectory().toAbsolutePath(), "esd-raw")
                val cores = Runtime.getRuntime().availableProcessors()
                val sdeDownloadExecutor = Executors.newFixedThreadPool(cores * 2)
                val downloadRunnables: MutableList<Callable<Outcome>> = ArrayList()

                for (category in SDECategory.values()) {
                    downloadRunnables.add(Callable<Outcome> {
                        val categoryUrl = category.categoryURL.openConnection().apply {
                            setRequestProperty(
                                "User-Agent",
                                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.87 Safari/537.36"
                            )
                        }
                        val downloadPath = Paths.get(esdRawDir.toString(), category.fileName)
                        try {
                            downloadFile(category.categoryURL, downloadPath)
                            Outcome.Success
                        } catch (e: IOException) {
                            Outcome.Failed("Failed to download SDE file at ${category.categoryURL}", e)
                        }
                    })
                }
                try {
                    val total = downloadRunnables.size
                    val futures = sdeDownloadExecutor.invokeAll(downloadRunnables)
                    var completed: Long = 0
                    while (completed < total) {
                        Thread.sleep(100L)
                        completed = futures.parallelStream().filter { f -> f.isDone }.count()
                    }
                    sdeDownloadExecutor.shutdown()
                    futures.firstOrNull { f -> f.get() is Outcome.Failed }?.let {
                        val outcome = it.get() as Outcome.Failed
                        callback.invoke(UpdateOutcome.BadUpdate(outcome.message, outcome.exception))
                        //TODO Cleanup after a failed ESD download
                        return
                    }
                } catch (e: InterruptedException) {
                    // TODO handle error
                }
                val om = jacksonObjectMapper()
                try {
                    for (c in SDECategory.values()) {
                        val materials = om.readValue<Array<ESDData>>(
                            Files.readAllBytes(Paths.get(esdRawDir.toString(), c.fileName)),
                            jacksonObjectMapper().typeFactory.constructArrayType(c.dataClass)
                        )
                        insertBulk(database, materials)
                    }
                }catch (e: Exception){
                    callback.invoke(UpdateOutcome.BadUpdate("", e))
                    return
                    // TODO Cleanup after failed ESD udpate
                }
                //TODO Cleanup after successful ESD update
                callback.invoke(UpdateOutcome.GoodUpdate)
            }
        }
    }

    private fun cleanupTempDownload(){

    }

    private inline fun <reified T: ESDData> insertBulk(database: Nitrite, d: Array<T>) {
        database.getRepository(T::class.javaObjectType).insert(d)
    }

    private fun downloadFile(url: URL, downloadPath: Path): String? {
        val connection = url.openConnection()
        connection.setRequestProperty(
            "User-Agent",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.87 Safari/537.36"
        )
        val file = downloadPath.toFile()
        if (file.exists()) {
            file.delete()
        }
        if (!file.parentFile.exists()) {
            Files.createDirectory(file.toPath())
        }
        try {
            file.outputStream().channel.transferFrom(
                Channels.newChannel(connection.getInputStream()),
                0,
                Long.MAX_VALUE
            )
        } catch (e: Exception) {
            return e.message
        }
        return null
    }

    override fun metadata(): ESDMetadata? {
        readDatabase().getRepository(ESDMetadata::class.java)
        return readDatabase().getRepository(ESDMetadata::class.java).find().firstOrDefault()
    }

    private fun updateMetadata(metadata: ESDMetadata){
        writeDatabase().getRepository(ESDMetadata::class.java).update(metadata, true)
    }

    private fun clearDatabase() {
        database.close()
        databasePath.toFile().delete()
        readDatabase()
    }

    private fun databaseExists(): Boolean {
        return Files.exists(databasePath)
    }

    private fun writeDatabase(): Nitrite {
        if(!database.context.isReadOnly){
            return database
        }
        database.close()
        database =  nitrite {
            autoCommitBufferSize = 2048
            compress = true
            autoCompact = true
            file = databasePath.toFile()
        }
        return database
    }

    private fun readDatabase(): Nitrite {
        if(database.context.isReadOnly){
            return database
        }
        database.close()
        database = nitrite {
            readOnly
            file = databasePath.toFile()
        }
        return database
    }

}