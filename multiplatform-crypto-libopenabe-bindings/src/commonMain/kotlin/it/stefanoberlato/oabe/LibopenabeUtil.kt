package it.stefanoberlato.oabe

expect object LibopenabeUtil {
    fun initializeOpenABE()
    fun initializeOpenABEWithoutOpenSSL()
    fun shutdownOpenABE()
    fun assertLibInit()
}