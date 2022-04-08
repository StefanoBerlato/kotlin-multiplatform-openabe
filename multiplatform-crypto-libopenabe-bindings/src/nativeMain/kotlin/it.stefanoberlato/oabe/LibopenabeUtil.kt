package it.stefanoberlato.oabe

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
}