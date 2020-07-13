package net.legio.eve.engine.data.document

import net.legio.eve.engine.data.DataItem
import net.legio.eve.engine.data.IEsiDataRepository
import org.dizitart.no2.FindOptions
import org.dizitart.no2.objects.ObjectFilter
import kotlin.reflect.KClass

interface IEsiDocumentDataRepository: IEsiDataRepository {

    fun <T: DataItem> find(options: FindOptions, klass: KClass<T>): List<T>

    fun <T: DataItem> find(filter: ObjectFilter, klass: KClass<T>): List<T>

}
