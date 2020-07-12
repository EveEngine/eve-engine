package net.legio.eve.engine.data

interface IEsiDataRepository {
    fun isInitialized(): Boolean
    fun clear()
}