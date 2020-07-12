package net.legio.eve.engine.data.document

import net.legio.eve.engine.data.IEsiDataRepository
import org.dizitart.no2.FindOptions
import org.dizitart.no2.objects.ObjectFilter
import kotlin.reflect.KClass

interface IEsiDocumentDataRepository: IEsiDataRepository {

    fun <T: DocumentItem> registerType(klass:KClass<T>)

    fun <T: DocumentItem> find(options: FindOptions, klass: KClass<T>): List<T>

    fun <T: DocumentItem> find(filter: ObjectFilter, klass: KClass<T>): List<T>

    fun <T: DocumentItem> insertBulk(items: Array<T>, klass: KClass<T>)

    fun <T: DocumentItem> insert(item: T, klass: KClass<T>)
}

internal inline fun <reified T: DocumentItem> IEsiDocumentDataRepository.registerType() = registerType(T::class)
