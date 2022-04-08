package it.stefanoberlato.oabe

import it.stefanoberlato.LibopenabeInitializer.openabeJna

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
}