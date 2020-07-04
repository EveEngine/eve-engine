package net.legio.eve.engine.data

import java.nio.file.Path

/**
 * Interface for a repository of ESD (Eve Static Data).
 */
interface ESDRepository {

    /**
     * The path on the file system to the database.
     */
    val databasePath: Path

    /**
     * Checks if the database has been initialized.
     */
    fun isInitialized(): Boolean

    /**
     * Determines if the database is up to date with current data or if it needs to be updated.
     */
    fun isUpToDate(): UpToDateCheck

    /**
     * Update the database with a callback to evaluate the outcome.
     */
    fun update(callback: (UpdateOutcome) -> Unit)

    /**
     * Get the current metadata of the database. This will return null if the database is not initialized.
     */
    fun metadata(): ESDMetadata?

    companion object {
        /**
         * The web address to the MD5 file to compare with.
         */
        val SDE_MD5_ADDR = "http://sde.zzeve.com/installed.md5"
    }
}