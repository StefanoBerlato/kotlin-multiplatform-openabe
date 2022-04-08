package it.stefanoberlato

expect object LibopenabeInitializer {
    /** Return true if the library was initialized */
    fun isInitialized() : Boolean

    /** Initialize the library */
    suspend fun initialize()

    /** Initialize the library with [callback] */
    fun initializeWithCallback(callback: () -> (Unit))
}
