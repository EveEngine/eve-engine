package net.legio.eve.engine.core

sealed class ReadResult
data class GoodRead(val content: String): ReadResult()
data class FailedRead(val error: String, val throwable: Throwable? = null): ReadResult()