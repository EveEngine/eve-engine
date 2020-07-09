package net.legio.eve.engine.data.document

import net.legio.eve.engine.data.ESDRepository
import net.legio.eve.engine.data.entity.ESDData
import org.dizitart.no2.FindOptions
import org.dizitart.no2.objects.ObjectFilter

/**
 * The focus of this type of repository is to expose read only functions. ESD (Eve Static Data) is just that, static,
 * therefore no updates, inserts or deletion functions should be exposed.
 */
interface IDocumentESDRepository : ESDRepository {
    fun initialize()
    fun <T: ESDData> find(options: FindOptions): Array<T>
    fun <T: ESDData> find(filter: ObjectFilter): Array<T>
}