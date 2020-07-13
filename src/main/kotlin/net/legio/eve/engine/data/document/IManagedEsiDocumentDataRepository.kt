package net.legio.eve.engine.data.document

import net.legio.eve.engine.data.DataItem
import kotlin.reflect.KClass

internal interface IManagedEsiDocumentDataRepository: IEsiDocumentDataRepository {

    fun <T: DataItem> insertBulk(items: Array<T>, klass: KClass<T>)

    fun <T: DataItem> insert(item: T, klass: KClass<T>)

    fun <T: DataItem> registerType(klass:KClass<T>)
}

internal inline fun <reified T: DataItem> IManagedEsiDocumentDataRepository.registerType() = registerType(T::class)