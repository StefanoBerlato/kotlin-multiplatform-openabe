package it.stefanoberlato.oabe

import it.stefanoberlato.SchemeID

/** Wrapper exception for any exception thrown by the OpenABE crypto context */
open class OpenABECryptoContextError(message: String): RuntimeException(message)

/** Generic exception for the OpenABE crypto context */
class OpenABECryptoContextRunTime(message: String = "Unexpected Runtime Error") :
    OpenABECryptoContextError(message)

/** Exception when decryption fails */
class OpenABECryptoContextDecrypt(message: String = "Exception during decryption, probably the ciphertext is corrupted or the key does not satisfy the policy") :
    OpenABECryptoContextError(message)

/** Exception when key deletion fails */
class OpenABECryptoContextKeyDelete(message: String = "Exception during key deletion, probably key does not exist") :
    OpenABECryptoContextError(message)

/** Exception when the context was previously destroyed */
class OpenABECryptoContextDestroyed(message: String = "This context was destroyed, do not use it, create a new one") :
    OpenABECryptoContextError(message)

/** Exception when the context is used without first generating or importing the parameters */
class OpenABECryptoContextMissingParameters(message: String = "This context does not have all needed public/secret parameters") :
    OpenABECryptoContextError(message)

/**
 * This is the struct containing the
 * void pointer to the context object
 *
 * struct openABECryptoContext {
 *     void *obj;
 * };
 */
expect class OpenABECryptoContextObject

/** This is an enum for error codes */
enum class State(val code: Int) {
    Success(1),
    UnexpectedError(2),
    DecryptionError(3),
    DeleteKeyError(4);

    companion object {
        fun fromInt(value: Int) = values().first { it.code == value }
    }
}

/**
 * This is a wrapper class exposing the
 * functionalities of the corresponding
 * openABECryptoContext class in OpenABE.
 * If you do not have a good reason, leave
 * the 'base64encode' argument to 'true'
 */
expect class OpenABECryptoContext(schemeID: SchemeID, base64encode: Boolean = true) {

    val context: OpenABECryptoContextObject

    /**
     * The context is usable only after having either
     * generated or imported the public/secret parameters
     */
    var publicParametersSetup: Boolean
    var secretParametersSetup: Boolean

    /**
     * The context is not usable after
     * having being destroyed
     */
    var destroyed: Boolean

    fun destroy()

    fun generateParams()

    fun keygen(
        keyInput: String,
        keyID: String,
        authID: String = "",
        GID: String = ""
    )

    fun encrypt(
        encInput: String,
        plaintext: String
    ): String

    fun decrypt(
        ciphertext: String
    ): String

    fun decrypt(
        keyID: String,
        ciphertext: String
    ): String

    fun enableKeyManager(
        userId: String
    )

    fun enableVerbose()

    fun exportPublicParams(): String

    fun exportSecretParams(): String

    fun importPublicParams(
        keyBlob: String
    )

    fun importSecretParams(
        keyBlob: String
    )

    fun importUserKey(
        keyID: String,
        keyBlob: String
    )

    fun exportUserKey(
        keyID: String
    ): String

    fun deleteKey(
        keyID: String
    ): Boolean
}