package net.legio.eve.engine.core

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import javax.annotation.PreDestroy
import kotlin.concurrent.thread
import kotlin.reflect.KClass

enum class Scope { Background, UI }

open class Event(val scope: Scope = Scope.UI)

internal class Subscription(val action: (Event) -> Unit) {
    var valid = true
}

/**
 * An event aggregation service that allows the subscription and publishing of events ID'd by a string name.
 */
abstract class EventBus {

    private val mappedActions: ConcurrentMap<KClass<out Event>, HashSet<Subscription>> = ConcurrentHashMap()

    @PreDestroy
    private fun destroy() {
        clearAll()
    }

    /**
     * Identifies if the current thread is the UI thread.
     */
    protected abstract fun isUIThread(): Boolean

    /**
     * Publishes a runnable to execute on the UI thread.
     */
    protected abstract fun publishOnUIThread(execute: (Runnable) -> Unit)

    inline fun <reified T : Event> subscribe(noinline action: (T) -> Unit) = subscribe<T>(T::class, action)

    @Suppress("UNCHECKED_CAST")
    fun <T : Event> subscribe(event: KClass<T>, action: (T) -> Unit) {
        mappedActions.getOrPut(event, { HashSet() }).add(Subscription(action as (Event) -> Unit))
    }

    inline fun <reified T : Event> unsubscribe(noinline action: (T) -> Unit) = unsubscribe(T::class, action)

    fun <T : Event> unsubscribe(event: KClass<T>, action: (T) -> Unit) {
        mappedActions[event]?.let { subs ->
            subs.forEach { sub -> if (sub.valid && sub.action == action) sub.valid = false }
        }
    }

    private fun cleanSubscriptions(event: KClass<Event>) {
        mappedActions.computeIfPresent(event) { _, subs ->
            subs.filter { sub -> sub.valid }.toHashSet()
        }
    }

    private fun clearAll() {
        mappedActions.clear()
    }

    private fun publish(runningScope: Scope, event: Event) {
        val kclass = event.javaClass.kotlin
        mappedActions[kclass]?.forEach { sub ->
            if (sub.valid && event.scope == runningScope) {
                sub.action.invoke(event)
            }
        }
        cleanSubscriptions(kclass)
    }

    fun publish(event: Event) {
        if (isUIThread()) {
            if (event.scope == Scope.UI) {
                publishOnUIThread { publish(Scope.UI, event) }
            }
            if (event.scope == Scope.Background) {
                thread(true) { publish(Scope.Background, event) }
            }
        } else { // Starting from background thread
            if (event.scope == Scope.UI) {
                publishOnUIThread { publish(Scope.UI, event) }
            }
            if (event.scope == Scope.Background) {
                thread(true) { publish(Scope.Background, event) }
            }
        }
    }
}