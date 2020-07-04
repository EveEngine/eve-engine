package net.legio.eve.engine

sealed class Outcome {
    object Success: Outcome()
    data class Failed(val message: String, val exception: Throwable? = null): Outcome()
}