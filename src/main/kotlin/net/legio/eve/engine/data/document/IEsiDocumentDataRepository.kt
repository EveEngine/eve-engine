package net.legio.eve.engine.data.document

import net.legio.eve.engine.data.IEsiDataRepository
import org.dizitart.no2.FindOptions
import org.dizitart.no2.objects.ObjectFilter

interface IEsiDocumentDataRepository: IEsiDataRepository {

    fun <T: DocumentItem> find(options: FindOptions): Array<T> {
        TODO("Not yet implemented")
    }

    fun <T: DocumentItem> find(filter: ObjectFilter): Array<T> {
        TODO("Not yet implemented")
    }

}