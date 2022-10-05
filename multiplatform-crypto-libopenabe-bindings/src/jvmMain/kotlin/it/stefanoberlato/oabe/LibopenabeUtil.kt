package it.stefanoberlato.oabe

import it.stefanoberlato.LibopenabeInitializer.openabeJna
import it.stefanoberlato.PointerTypeString

actual object LibopenabeUtil {

    actual fun initializeOpenABE() {
        openabeJna.InitializeOpenABE()
    }

    actual fun initializeOpenABEWithoutOpenSSL() {
        openabeJna.InitializeOpenABEwithoutOpenSSL()
    }

    actual fun shutdownOpenABE() {
        openabeJna.ShutdownOpenABE()
    }

    actual fun assertLibInit() {
        openabeJna.AssertLibInit()
    }

    fun freeAndReturn(pointer: PointerTypeString) : String {
        val string = pointer.string
        pointer.free()
        return string
    }
}