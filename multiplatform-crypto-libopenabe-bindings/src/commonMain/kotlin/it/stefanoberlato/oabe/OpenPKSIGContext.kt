package it.stefanoberlato.oabe

/**
 * Wrapper exception for any exception
 * thrown by the OpenABE PKSIG crypto context
 */
open class OpenPKSIGContextError(message: String): RuntimeException(message)

/** Generic exception for the OpenABE PKSIG context */
class OpenPKSIGContextRunTime(
    message: String = "Unexpected PKSIG Runtime Error"
) : OpenPKSIGContextError(message)

/** Exception when signing fails */
class OpenPKSIGContextSign(
    message: String = "Exception during PKSIG signing"
) : OpenPKSIGContextError(message)

/** Exception when verification fails */
class OpenPKSIGContextVerify(
    message: String = "Exception during PKSIG verification"
) : OpenPKSIGContextError(message)

/** Exception when the context was previously destroyed */
class OpenPKSIGContextDestroyed(
    message: String = "This PKSIG context was destroyed, do not use it, create a new one"
) : OpenPKSIGContextError(message)

/** Exception when key export fails */
class OpenPKSIGContextExportKey(
    message: String = "Exception during PKSIG ket export, probably the" +
            " key does not exist"
) : OpenPKSIGContextError(message)



/**
 * This is the struct containing the
 * void pointer to the context object
 *
 * struct openPKSIGContext {
 *     void *obj;
 * };
 */
expect class OpenPKSIGContextObject



/**
 * This is a wrapper class exposing the
 * functionalities of the corresponding
 * OpenPKSIGContext class in OpenABE.
 * [ecID] can only be [NIST_P256].
 * If you do not have a good reason, leave
 * the [base64encode] argument to 'true'
 */
expect class OpenPKSIGContext(
    ecID: ECID,
    base64encode: Boolean = true
) {

    /**
     * The (handle to the PKSIG object) context
     */
    val context: OpenPKSIGContextObject

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
     * Generate a PKSIG key with the given [keyID] (arbitrary
     * string that is appended to the key blob. Therefore, a
     * key, once created, must always have the same key ID, even
     * when imported elsewhere)
     */
    fun keygen(
        keyID: String
    )

    /**
     * Sign the given [message] with the given
     * [keyID], and return the signature
     */
    fun sign(
        keyID: String,
        message: String
    ): String

    /**
     * Verify the [signature] for the [message] with
     * the given [keyID]
     */
    fun verify(
        keyID: String,
        message: String,
        signature: String
    )

    /**
     * Export the PKSIG public key with
     * the specified [keyID]
     */
    fun exportPublicKey(
        keyID: String
    ): String

    /**
     * Export the PKSIG private key with
     * the specified [keyID]
     */
    fun exportPrivateKey(
        keyID: String
    ): String

    /**
     * Import a PKSIG public key [keyBlob] with
     * the specified [keyID] (which must
     * be the same with which the key was
     * generated)
     */
    fun importPublicKey(
        keyID: String,
        keyBlob: String
    )

    /**
     * Import a PKSIG private key [keyBlob] with
     * the specified [keyID] (which must
     * be the same with which the key was
     * generated)
     */
    fun importPrivateKey(
        keyID: String,
        keyBlob: String
    )
}
