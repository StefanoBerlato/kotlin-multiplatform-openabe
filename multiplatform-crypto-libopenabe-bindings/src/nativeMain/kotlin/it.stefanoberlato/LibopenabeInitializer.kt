@file:Suppress("VARIABLE_IN_SINGLETON_WITHOUT_THREAD_LOCAL")

package it.stefanoberlato

import it.stefanoberlato.oabe.LibopenabeUtil
import kotlin.native.concurrent.AtomicInt

actual object LibopenabeInitializer {

    private var isPlatformInitialized: AtomicInt = AtomicInt(0)

    actual suspend fun initialize() {
        if (isPlatformInitialized.compareAndSet(0, 1)) {
            LibopenabeUtil.initializeOpenABE()
        }
    }

    actual fun initializeWithCallback(callback: () -> Unit) {
        if (isPlatformInitialized.compareAndSet(0, 1)) {
            LibopenabeUtil.initializeOpenABE()
        }
        callback()
    }

    actual suspend fun shutdown() {
        if (isPlatformInitialized.compareAndSet(1, 0)) {
            LibopenabeUtil.shutdownOpenABE()
        }
    }

    actual fun isLibraryLoadedYet(): Boolean {
        return isPlatformInitialized.value != 0
    }

    actual fun isPlatformInitializedYet(): Boolean {
        return isPlatformInitialized.value != 0
    }
}