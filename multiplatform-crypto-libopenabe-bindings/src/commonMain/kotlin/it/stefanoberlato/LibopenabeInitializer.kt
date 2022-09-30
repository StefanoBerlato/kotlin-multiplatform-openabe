package it.stefanoberlato

expect object LibopenabeInitializer {

    /** Initialize the library */
    suspend fun initialize()

    /** Initialize the library with [callback] */
    fun initializeWithCallback(callback: () -> (Unit))

    /** Shutdown the library */
    suspend fun shutdown()

    /** Return true if the library was loaded */
    fun isLibraryLoadedYet(): Boolean

    /** Return true if the library was initialized */
    fun isPlatformInitializedYet(): Boolean

}
