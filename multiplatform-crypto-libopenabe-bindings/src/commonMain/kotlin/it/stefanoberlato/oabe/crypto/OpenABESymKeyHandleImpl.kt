package it.stefanoberlato.oabe.crypto

/**
 * Wrapper exception for any exception
 * thrown by the OpenABE symmetric key
 * handle implementation
 */
open class OpenABESymKeyHandleImplError(message: String): RuntimeException(message)

/**
 * Generic exception for the OpenABE
 * symmetric key handle implementation
 */
class OpenABESymKeyHandleImplRunTime(
    message: String = "Unexpected SymKeyHandleImpl Runtime Error"
) : OpenABESymKeyHandleImplError(message)

/** Exception when encryption fails */
class OpenABESymKeyHandleImplEncrypt(
    message: String = "Exception during Sym encryption"
) : OpenABESymKeyHandleImplError(message)

/** Exception when decryption fails */
class OpenABESymKeyHandleImplDecrypt(
    message: String = "Exception during Sym decryption"
) : OpenABESymKeyHandleImplError(message)

/** Exception when the context was previously destroyed */
class OpenABESymKeyHandleImplDestroyed(
    message: String = "This Sym context was destroyed, do not use it, create a new one"
) : OpenABESymKeyHandleImplError(message)


/**
 * This is the struct containing the
 * void pointer to the context object
 *
 * struct openABESymKeyHandleImpl {
 *     void *obj;
 * };
 */
expect class OpenABESymKeyHandleImplObject



/**
 * This is a wrapper class exposing the
 * functionalities of the corresponding
 * OpenABESymKeyHandleImpl class in OpenABE.
 * If you do not have a good reason, leave
 * the [applyB64Encode] argument to 'true'
 */
expect class OpenABESymKeyHandleImpl(
    keyBytes: ByteArray,
    applyB64Encode: Boolean = true
) {

    // TODO "OpenABESymKeyHandleImpl" has another constructor
    //  (see below) in "zsymcrypto.h" for which we should
    //  create the bindings.
    //  """
    //  OpenABESymKeyHandleImpl(OpenABEByteString& keyBytes,
    //      OpenABEByteString& authData,
    //      bool apply_b64_encode = false);
    //  """

    val context: OpenABESymKeyHandleImplObject

    /**
     * The context is not usable after
     * having being destroyed
     */
    var destroyed: Boolean

    fun destroy()

    fun encrypt(
        plaintext: String
    ): String

    fun decrypt(
        ciphertext: String
    ): String

    fun exportRawKey(): String

    fun exportKey(): String
}
