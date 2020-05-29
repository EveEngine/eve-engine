package net.legio.eve.engine.core

/**
 * A convenience class that defines a service or component that should be initialized at startup.
 * Architecturally, this would be for a splash screen or pre-app.
 */
abstract class StartupComponent {

    /**
     * Called when the component should do the work of loading data or fetching resources.
     * @param progressUpdate A callback that takes a String describing the startup processes.
     */
    fun startupWithProgressString(progressUpdate: (String) -> Unit){}

    /**
     * Called when the component should do the work of loading data or fetching resources.
     * @param progressUpdate A callback that takes an Int describing the startup progress. The value
     * should typically be between 0 and 100.
     */
    fun startupWithProgressCounter(progressUpdate: (Int) -> Unit) {}

}