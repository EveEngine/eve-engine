package net.legio.eve.engine.data

sealed class UpToDateCheck {
    object UpToDate : UpToDateCheck()
    object OutOfDate : UpToDateCheck()
    data class BadCheck(val error: String, val exception: Throwable? = null) : UpToDateCheck()
}