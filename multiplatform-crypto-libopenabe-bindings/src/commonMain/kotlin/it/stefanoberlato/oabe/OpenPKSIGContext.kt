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
 * If you do not have a good reason, leave
 * the [base64encode] argument to 'true'
 */
expect class OpenPKSIGContext(
    ecID: ECID,
    base64encode: Boolean = true
) {

    val context: OpenPKSIGContextObject

    /**
     * The context is not usable after
     * having being destroyed
     */
    var destroyed: Boolean

    fun destroy()

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

    fun keygen(
        keyID: String
    )

    fun sign(
        keyID: String,
        message: String
    ): String

    fun verify(
        keyID: String,
        message: String,
        signature: String
    )
}
