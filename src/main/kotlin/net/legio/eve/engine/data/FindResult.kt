package net.legio.eve.engine.data

import net.legio.eve.engine.data.entity.ESDData

sealed class FindResult
class GoodResult<T: ESDData>(val data: Array<T>): FindResult()
object NotFoundResult: FindResult()
class ErrorResult(val error: String?): FindResult()