package it.stefanoberlato.oabe

import it.stefanoberlato.oabe.SchemeID.CP_ABE
import it.stefanoberlato.oabe.SchemeID.KP_ABE

/**
 * Wrapper exception for any exception
 * thrown by the OpenABE ABE crypto context
 */
open class OpenABECryptoContextError(message: String): RuntimeException(message)

/** Generic exception for the OpenABE crypto context */
class OpenABECryptoContextRunTime(
    message: String = "Unexpected ABE Runtime Error"
) : OpenABECryptoContextError(message)

/** Exception when decryption fails */
class OpenABECryptoContextDecrypt(
    message: String = "Exception during ABE decryption, probably the" +
    " ciphertext corrupted or the key does not satisfy the policy"
) : OpenABECryptoContextError(message)

/** Exception when key deletion fails */
class OpenABECryptoContextKeyDelete(
    message: String = "Exception during ABE key deletion, probably key does not exist"
) : OpenABECryptoContextError(message)

/** Exception when the context was previously destroyed */
class OpenABECryptoContextDestroyed(
    message: String = "This ABE context was destroyed, do not use it, create a new one"
) : OpenABECryptoContextError(message)

/** Exception when key export fails */
class OpenABECryptoContextExportKey(
    message: String = "Exception during ABE key export, probably the" +
            " key does not exist or the key manager was not enabled"
) : OpenABECryptoContextError(message)

/**
 * Exception when the context is used without
 * first generating or importing the parameters
 */
class OpenABECryptoContextMissingParameters(
    message: String = "This ABE context does not have all needed public/secret parameters"
) : OpenABECryptoContextError(message)



/**
 * This is the struct containing the
 * void pointer to the context object
 *
 * struct openABECryptoContext {
 *     void *obj;
 * };
 */
expect class OpenABECryptoContextObject



/**
 * This is a wrapper class exposing the
 * functionalities of the corresponding
 * OpenABECryptoContext class in OpenABE.
 * Choose [schemeID] among [CP_ABE] and
 * [KP_ABE]. If you do not have a good reason,
 * leave the [base64encode] argument to 'true'
 */
expect class OpenABECryptoContext(
    schemeID: SchemeID,
    base64encode: Boolean = true
) {

    /**
     * The (handle to the OpenABE object) context
     */
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

    /**
     * Destroy the context and free the memory
     */
    fun destroy()

    /**
     * Generate ABE parameters. Intuitively, this function
     * should be invoked before other functions (unless you
     * import the public and the secret parameters)
     */
    fun generateParams()

    /**
     * Generate an ABE key with the given [keyInput]
     * (if the scheme is CP-ABE, [keyInput] is the attribute
     * list, while if the scheme is KP-ABE, [keyInput] is
     * the access structure. In both cases, the [keyInput]
     * is appended at the end of the key blob), [keyID]
     * (arbitrary string that is appended to the key blob. Therefore,
     * a key, once created, must always have the same key ID, even
     * when imported elsewhere). You can safely ignore [authID] and
     * [GID] as these parameters regard multi-authority ABE (which
     * is not supported by the free version of OpenABE)
     */
    fun keygen(
        keyInput: String,
        keyID: String,
        authID: String = "",
        GID: String = ""
    )

    /**
     * Encrypt the given [plaintext] under the given [encInput]
     * (if the scheme is CP-ABE, [encInput] is the access structure,
     * while if the scheme is KP-ABE, [encInput] is the attribute list)
     * and return the ciphertext
     */
    fun encrypt(
        encInput: String,
        plaintext: String
    ): String

    /**
     * Decrypt the given [ciphertext]. No key ID is specified, thus
     * OpenABE looks for a (previously imported or generated key)
     * which can decrypt the [ciphertext] (the key manager must have
     * been enabled in both cases), and return the plaintext
     */
    fun decrypt(
        ciphertext: String
    ): String

    /**
     * Decrypt the given [ciphertext] with the key with the
     * (previously imported or generated key) [keyID] (no
     * key manager required), and return the plaintext
     */
    fun decrypt(
        keyID: String,
        ciphertext: String
    ): String

    /**
     * Enable the key manager with [userID]
     */
    fun enableKeyManager(
        userID: String
    )

    /**
     * Enable verbose logging
     */
    fun enableVerbose()

    /**
     * Export the ABE public parameters
     */
    fun exportPublicParams(): String

    /**
     * Export the ABE secret parameters
     */
    fun exportSecretParams(): String

    /**
     * Import the ABE public parameters
     */
    fun importPublicParams(
        keyBlob: String
    )

    /**
     * Import the ABE secret parameters
     */
    fun importSecretParams(
        keyBlob: String
    )

    /**
     * Import an ABE key [keyBlob] with
     * the specified [keyID] (which must
     * be the same with which the key was
     * generated)
     */
    fun importUserKey(
        keyID: String,
        keyBlob: String
    )

    /**
     * Export the ABE key with the specified [keyID]
     */
    fun exportUserKey(
        keyID: String
    ): String

    /**
     * Delete the ABE key with the specified [keyID]
     */
    fun deleteKey(
        keyID: String
    ): Boolean
}
