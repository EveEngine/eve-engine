package net.legio.eve.engine.data.document

import java.io.Serializable
import kotlin.reflect.KClass

internal class RepoRegistry: Serializable{

    private val repoKeyMap: HashMap<String, String> = HashMap()

    fun repoKeys(): Set<String> {
        return repoKeyMap.keys
    }

    fun klassForKey(key: String): KClass<*>? {
        return Class.forName(repoKeyMap[key])::class
    }

    fun <T: Any> registerKey(key: String, klass: KClass<T>) {
        repoKeyMap[key] = klass.qualifiedName!!
    }

    fun removeKey(key: String){
        repoKeyMap.remove(key)
    }

}