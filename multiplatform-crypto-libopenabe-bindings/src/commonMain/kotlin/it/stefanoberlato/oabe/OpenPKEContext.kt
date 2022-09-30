package it.stefanoberlato.oabe

import it.stefanoberlato.oabe.SchemeID.CP_ABE
import it.stefanoberlato.oabe.SchemeID.KP_ABE

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
 * [ecID] can only be [NIST_P256].
 * If you do not have a good reason, leave
 * the [base64encode] argument to 'true'
 */
expect class OpenPKEContext(
    ecID: ECID,
    base64encode: Boolean = true
) {

    /**
     * The (handle to the PKE object) context
     */
    val context: OpenPKEContextObject

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
     * Generate a PKE key with the given [keyID] (arbitrary
     * string that is appended to the key blob. Therefore, a
     * key, once created, must always have the same key ID, even
     * when imported elsewhere)
     */
    fun keygen(
        keyID: String
    )

    /**
     * Encrypt the given [plaintext] for the given
     * [receiverID], and return the ciphertext
     */
    fun encrypt(
        receiverID: String,
        plaintext: String
    ): String

    /**
     * Decrypt the given [ciphertext] with the given
     * [receiverID], and return the plaintext
     */
    fun decrypt(
        receiverID: String,
        ciphertext: String
    ): String

    /**
     * Export the PKE public key with
     * the specified [keyID]
     */
    fun exportPublicKey(
        keyID: String
    ): String

    /**
     * Export the PKE private key with
     * the specified [keyID]
     */
    fun exportPrivateKey(
        keyID: String
    ): String

    /**
     * Import a PKE public key [keyBlob] with
     * the specified [keyID] (which must
     * be the same with which the key was
     * generated)
     */
    fun importPublicKey(
        keyID: String,
        keyBlob: String
    )

    /**
     * Import a PKE private key [keyBlob] with
     * the specified [keyID] (which must
     * be the same with which the key was
     * generated)
     */
    fun importPrivateKey(
        keyID: String,
        keyBlob: String
    )
}