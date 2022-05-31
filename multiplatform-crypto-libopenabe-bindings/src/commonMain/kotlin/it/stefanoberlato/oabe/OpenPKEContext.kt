package it.stefanoberlato.oabe

/**
 * Wrapper exception for any exception
 * thrown by the OpenABE PKE crypto context
 */
open class OpenPKEContextError(message: String): RuntimeException(message)

/** Generic exception for the OpenABE PKE context */
class OpenPKEContextRunTime(
    message: String = "Unexpected PKE Runtime Error"
) : OpenPKEContextError(message)

/** Exception when encryption fails */
class OpenPKEContextEncrypt(
    message: String = "Exception during PKE encryption"
) : OpenPKEContextError(message)

/** Exception when decryption fails */
class OpenPKEContextDecrypt(
    message: String = "Exception during PKE decryption"
) : OpenPKEContextError(message)

/** Exception when the context was previously destroyed */
class OpenPKEContextDestroyed(
    message: String = "This PKE context was destroyed, do not use it, create a new one"
) : OpenPKEContextError(message)

/** Exception when key export fails */
class OpenPKEContextExportKey(
    message: String = "Exception during PKE ket export, probably the" +
            " key does not exist"
) : OpenPKEContextError(message)



/**
 * This is the struct containing the
 * void pointer to the context object
 *
 * struct openPKEContext {
 *     void *obj;
 * };
 */
expect class OpenPKEContextObject



/**
 * This is a wrapper class exposing the
 * functionalities of the corresponding
 * OpenPKEContext class in OpenABE.
 * If you do not have a good reason, leave
 * the [base64encode] argument to 'true'
 */
expect class OpenPKEContext(
    ecID: ECID,
    base64encode: Boolean = true
) {

    val context: OpenPKEContextObject

    /**
     * The context is not usable after
     * having being destroyed
     */
    var destroyed: Boolean

    fun destroy()

    fun keygen(
        keyID: String
    )

    fun encrypt(
        receiverID: String,
        plaintext: String
    ): String

    fun decrypt(
        receiverID: String,
        ciphertext: String
    ): String

    fun exportPublicKey(
        keyID: String
    ): String

    fun exportPrivateKey(
        keyID: String
    ): String

    fun importPublicKey(
        keyID: String,
        keyBlob: String
    )

    fun importPrivateKey(
        keyID: String,
        keyBlob: String
    )
}