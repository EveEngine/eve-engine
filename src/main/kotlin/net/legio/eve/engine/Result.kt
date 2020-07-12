package net.legio.eve.engine

sealed class Result<out T>
class Success<out T> internal constructor(val data: T? = null): Result<T>()
class Failed<out T> internal constructor(val error: String? = null, val throwable: Throwable? = null): Result<T>()

fun <T> success(data: T? = null) = Success<T>(data)
fun <T> failed(error: String?, throwable: Throwable? = null) = Failed<T>(error, throwable)