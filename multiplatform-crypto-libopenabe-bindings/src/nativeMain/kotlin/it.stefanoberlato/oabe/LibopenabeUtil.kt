package it.stefanoberlato.oabe

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString

actual object LibopenabeUtil {

    actual fun initializeOpenABE() {
        libwrapper.InitializeOpenABE()
    }

    actual fun initializeOpenABEWithoutOpenSSL() {
        libwrapper.InitializeOpenABEwithoutOpenSSL()
    }

    actual fun shutdownOpenABE() {
        libwrapper.ShutdownOpenABE()
    }

    actual fun assertLibInit() {
        libwrapper.AssertLibInit()
    }

    fun cloneDeallocAndReturn(pointer: CPointer<ByteVar /* = ByteVarOf<Byte> */>) : String {
        val clonedString = "" + pointer.toKString()
        libwrapper.freeString(pointer)
        return clonedString
    }
}