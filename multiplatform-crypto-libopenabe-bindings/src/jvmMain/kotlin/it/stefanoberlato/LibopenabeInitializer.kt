package it.stefanoberlato

import com.goterl.resourceloader.SharedLibraryLoader
import com.sun.jna.Native
import com.sun.jna.Platform
import java.io.File
import java.lang.RuntimeException

actual object LibopenabeInitializer {
    private var isPlatformInitialized = false

    private fun loadLibrary() : JnaLibopenabeInterface {
        val libraryFile = when {
            Platform.isMac() -> {
                //SharedLibraryLoader.get().load("dynamic-macos.dylib", JnaLibopenabeInterface::class.java)
                throw RuntimeException("Unsupported platform") // TODO add support
            }
            Platform.isLinux() -> {
                if (Platform.isARM()) {
                    //SharedLibraryLoader.get().load("dynamic-linux-arm64-libopenabe.so", JnaLibopenabeInterface::class.java)
                    throw RuntimeException("Unsupported platform") // TODO add support
                } else {
                    SharedLibraryLoader.get().load(
                        "dynamic-linux-x86-64-libgmp.so",
                        JnaLibopenabeInterface::class.java
                    )
                    SharedLibraryLoader.get().load(
                        "dynamic-linux-x86-64-librelic.so",
                        JnaLibopenabeInterface::class.java
                    )
                    SharedLibraryLoader.get().load(
                        "dynamic-linux-x86-64-librelic_ec.so",
                        JnaLibopenabeInterface::class.java
                    )
                    SharedLibraryLoader.get().load(
                        "dynamic-linux-x86-64-libopenabe.so",
                        JnaLibopenabeInterface::class.java
                    )
                    SharedLibraryLoader.get().load(
                        "dynamic-linux-x86-64-libwrapper.so",
                        JnaLibopenabeInterface::class.java
                    )
                }
            }
            Platform.isWindows() -> {
                //SharedLibraryLoader.get().load("dynamic-msvc-x86-64-libopenabe.dll", JnaLibopenabeInterface::class.java)
                throw RuntimeException("Unsupported platform") // TODO add support
            }
            Platform.isAndroid() -> {
                when {
                    Platform.is64Bit() -> {
                        File("irrelevant")
                    }
                    else -> throw RuntimeException("Unsupported platform")
                }
            }
            else -> throw RuntimeException("Unknown platform")
        }

        val library = if (Platform.isAndroid()) {
            //Native.load("openabe", JnaLibopenabeInterface::class.java) as JnaLibopenabeInterface
            throw RuntimeException("Unsupported platform") // TODO add support
        } else {
            Native.load(
                libraryFile.absolutePath,
                JnaLibopenabeInterface::class.java
            ) as JnaLibopenabeInterface
        }

        return library
    }


    lateinit var openabeJna : JnaLibopenabeInterface
    actual suspend fun initialize() {
        if (!isPlatformInitialized) {
            openabeJna = loadLibrary()
            openabeJna.InitializeOpenABE()
            isPlatformInitialized = true
        }
    }

    actual fun initializeWithCallback(callback: () -> Unit) {
        if (!isPlatformInitialized) {
            openabeJna = loadLibrary()
            openabeJna.InitializeOpenABE()
            isPlatformInitialized = true
        }
        callback()
    }

    actual fun isInitialized(): Boolean {
        return isPlatformInitialized
    }
}