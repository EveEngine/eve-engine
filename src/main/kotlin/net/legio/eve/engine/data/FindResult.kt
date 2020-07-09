package net.legio.eve.engine.data

sealed class FindResult
class Found<T>(val data: Array<T>): FindResult()
object NotFound: FindResult()
class FindError(val error: String?): FindResult()