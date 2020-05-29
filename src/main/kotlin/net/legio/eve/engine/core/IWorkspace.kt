package net.legio.eve.engine.core

import java.nio.charset.Charset
import java.nio.file.Path

/**
 * Interfaces with the designed workspace directory structure. This should be used when
 * working with files that are supported directly by the workspace and should have a
 * know location on the file system and in a consistent manner.
 */
interface IWorkspace {
    fun rootDirectory(): Path
    fun dataDirectory(): Path
    fun cacheDirectory(): Path

    fun readFileAsString(directory: WorkspaceDirectory, fileName: String, charset: Charset): ReadResult
    fun readFileAsString(directory: WorkspaceDirectory, fileName: String): ReadResult
}

/**
 * Defines directories of the workspace.
 * @property value The literal directory name.
 */
enum class WorkspaceDirectory(val value: String) {
    /**
     * The root workspace directory. Contains all the files and sub-directories that make up
     * the workspace.
     */
    ROOT("eve-engine"),

    /**
     * The workspace data directory. Contains files and sub-directories that pertain to data
     * storage and management.
     */
    DATA("data"),

    /**
     * The workspace cache directory. Contains files and sub-directories that pertain to
     * special data caches that are distinct from the data directory..
     */
    CACHE("cache")
}
