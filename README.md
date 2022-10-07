# Kotlin Multiplatform for OpenABE

A wrapper allowing to easily use the [OpenABE](https://github.com/zeutro/openabe) library for Attribute-based Encryption (ABE) from Kotlin multiplatform.


## Warning

The bindings are still being developed, thus the wrapper is incomplete and may potentially contain bugs and vulnerabilities. Feel free to open an issue or contact me directly for more information or for contributing.


## Status

Currently, bindings are available for a portion of the APIs offered by OpenABE:
* the main utility functions in `openabe.h` (i.e., `InitializeOpenABE()`, `InitializeOpenABEwithoutOpenSSL()`, `ShutdownOpenABE()`, `AssertLibInit()`);
* the `OpenABECryptoContext`, `OpenPKEContext` and `OpenPKSIGContext` classes in `zcrypto_box.h`;
* the `OpenABESymKeyHandleImpl` class in `zsymcrypto.h`.

At the moment, supported targets are JVM (linuxX64) and linuxX64.


## Installation

```gradle
repositories {
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}
implementation("it.stefanoberlato:multiplatform-crypto-libopenabe-bindings:0.0.20-SNAPSHOT")
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

    /** Shutdown the library to free allocated memory*/
    shutdownOpenABE()
}
```

The `decrypt` function will throw a `OpenABECryptoContextDecrypt` exception if the ciphertext cannot be decrypted (because, e.g., the key does not embed the necessary attributes or the ciphertext was tampered with).

Finally, you can de-initialize the library.

```kotlin
suspend fun shutdownOpenABE() {
    ...
    LibopenabeInitializer.shutdown()
    ...
}
```


## Thanks

This project was inspired by [kotlin-multiplatform-libsodium](https://github.com/ionspin/kotlin-multiplatform-libsodium).
