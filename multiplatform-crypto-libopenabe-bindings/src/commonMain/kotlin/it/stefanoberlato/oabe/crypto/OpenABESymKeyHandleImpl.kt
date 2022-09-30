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
 * [keyBytes] are the bytes of the symmetric key
 * (e.g., generated with Zsymcrypto.generateSymmetricKey)
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
    /**
     * The (handle to the symmetric key object) context
     */
    val context: OpenABESymKeyHandleImplObject

    /**
     * The context is not usable after
     * having being destroyed
     */
    var destroyed: Boolean

    /**
     * Destroy the context and free the memory
     */
    fun destroy()

    /**
     * Decrypt the [plaintext] with the
     * symmetric key given during the creation
     * of the context and return the ciphertext
     */
    fun encrypt(
        plaintext: String
    ): String

    /**
     * Decrypt the [ciphertext] with the
     * symmetric key given during the creation
     * of the context and return the plaintext
     */
    fun decrypt(
        ciphertext: String
    ): String

    /**
     * Export the (raw) symmetric key
     */
    fun exportRawKey(): String

    /**
     * Export the symmetric key
     */
    fun exportKey(): String
}
