package net.legio.eve.engine.data

sealed class UpdateOutcome {
    object GoodUpdate: UpdateOutcome()
    data class BadUpdate(val message: String, val exception: Throwable? = null): UpdateOutcome()
}