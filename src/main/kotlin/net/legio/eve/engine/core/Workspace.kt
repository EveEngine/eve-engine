package net.legio.eve.engine.core

import org.springframework.stereotype.Component
import java.io.FileNotFoundException
import java.lang.IllegalArgumentException
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Component
class Workspace : IWorkspace{

    private final var mRootPath: Path
    private final var mDataPath: Path
    private final var mCachePath: Path

    init {
        val userHome = System.getProperty("user.home")
        if(userHome.isEmpty()){
            throw FileNotFoundException("The user.home System property was not found. Unable to establish workspace.")
        }
        val userHomePath = Paths.get(userHome)
        if(!Files.isDirectory(userHomePath)){
            throw IllegalArgumentException("The user.home System property value is not a directory. Unable to establish workspace.")
        }
        this.mRootPath = Paths.get(userHome, WorkspaceDirectory.ROOT.value)
        this.mDataPath = Paths.get(this.mRootPath.toAbsolutePath().toString(), WorkspaceDirectory.DATA.value)
        this.mCachePath = Paths.get(this.mRootPath.toAbsolutePath().toString(), WorkspaceDirectory.CACHE.value)
        setupWorkspace()
    }

    @Throws(InitializationException::class)
    private fun setupWorkspace(){
        try {
            if (!Files.exists(rootDirectory())) {
                Files.createDirectory(rootDirectory())
            }
            if (!Files.exists(dataDirectory())) {
                Files.createDirectory(dataDirectory())
            }
            if (!Files.exists(cacheDirectory())) {
                Files.createDirectory(cacheDirectory())
            }
        }catch (e: Exception){
            throw InitializationException("Failed to initialize workspace.", e)
        }
    }

    override fun rootDirectory(): Path = this.mRootPath
    override fun dataDirectory(): Path = this.mDataPath
    override fun cacheDirectory(): Path = this.mCachePath

    private fun pathForDirectory(directory: WorkspaceDirectory): Path {
        return when(directory){
            WorkspaceDirectory.ROOT -> this.rootDirectory()
            WorkspaceDirectory.DATA -> this.dataDirectory()
            WorkspaceDirectory.CACHE -> this.cacheDirectory()
        }
    }


    override fun readFileAsString(directory: WorkspaceDirectory, fileName: String): ReadResult {
        return readFileAsString(directory, fileName, Charset.defaultCharset())
    }

    override fun readFileAsString(directory: WorkspaceDirectory, fileName: String, charset: Charset): ReadResult {
        val dirPath = pathForDirectory(directory)
        val filePath = Paths.get(dirPath.toAbsolutePath().toString(), fileName)
        if(!Files.exists(filePath)){
            return FailedRead("File [$filePath] does not exist.")
        }
        return try{
            GoodRead(Files.readAllBytes(filePath).toString(charset))
        } catch (ex: Exception){
            FailedRead("Unable to read content from file [$filePath].", ex)
        }
    }
}