# Kotlin Multiplatform for OpenABE

A wrapper allowing to easily use the [OpenABE](https://github.com/zeutro/openabe) library for Attribute-based Encryption (ABE) from Kotlin multiplatform.


## Warning

The development just started (04/2022), thus the wrapper is still incomplete and potentially with bugs and vulnerabilities. Feel free to open an issue or contact me directly for more information or for contributing.


## Status

Currently, bindings are available for a small portion of the APIs offered by OpenABE:
* the main utility functions in `openabe.h` (i.e., `InitializeOpenABE()`, `InitializeOpenABEwithoutOpenSSL()`, `ShutdownOpenABE()`, `AssertLibInit()`);
* the `OpenABECryptoContext` and `OpenPKEContext` classes in `zcrypto_box.h`;
* the `OpenABESymKeyHandleImpl` class in `zsymcrypto.h`.

At the moment, the supported targets are JVM (linuxX64) and linuxX64. However, the bindings for the linuxX64 native target present incorrect behaviour for some inputs (e.g., decrypting a ciphertext previously encrypted with two attributes with an AND gate fails).


## Installation

```gradle
repositories {
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}
implementation("it.stefanoberlato:multiplatform-crypto-libopenabe-bindings:0.0.12-SNAPSHOT")
```

## Usage

Before using the OpenABE library, you need to initialize it through either coroutines or a callback.

```kotlin
suspend fun initializeOpenABE() {
    ...
    LibopenabeInitializer.initialize()
    ...
}
```

```kotlin
LibopenabeInitializer.initializeWithCallback {
    ... 
}
```

Afterwards, you can invoke the APIs of OpenABE as needed. For instance, below you can find an example of how to encrypt and decrypt a message with `OpenABECryptoContext`.

```kotlin
/** Initialize the library with a callback */
LibopenabeInitializer.initializeWithCallback {

    /** Create a context to use ABE primitives */
    val oabe = OpenABECryptoContext(SchemeID.CP_ABE)

    /**
     * Generate master secret and public keys
     * (you can also export/import parameters).
     */
    oabe.generateParams()

    /**
     * Generate a key with ID 'key0' corresponding
     * to the attributes 'attr1' and 'attr2'
     */
    oabe.keygen("|attr1|attr2", "key0")

    /** Encrypt under the access structure 'attr1 and attr2' */
    val plaintext = "Hello! I'm using Kotlin Multiplatform OpenABE."
    val ciphertext = oabe.encrypt("attr1 and attr2", plaintext)

    /** Decrypt specifying the key ID */
    val decrypted = oabe.decrypt("key0", ciphertext)

    assertEquals(plaintext, decrypted)

    /** Destroy the context to free allocated memory*/
    oabe.destroy()

    /** Shutdown the library to free allocated memory*/
    shutdownOpenABE()
}
```

The `decrypt` function will throw a `OpenABECryptoContextDecrypt` exception if the ciphertext cannot be decrypted (because, e.g., the key does not embed the necessary attributes or the ciphertext was tampered with).


## Thanks

This project was inspired by [kotlin-multiplatform-libsodium](https://github.com/ionspin/kotlin-multiplatform-libsodium).
