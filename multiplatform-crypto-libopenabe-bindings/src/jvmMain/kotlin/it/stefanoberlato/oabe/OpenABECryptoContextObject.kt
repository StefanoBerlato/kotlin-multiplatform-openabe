package it.stefanoberlato.oabe

import com.sun.jna.Structure
import com.sun.jna.ptr.PointerByReference
import it.stefanoberlato.LibopenabeInitializer
import it.stefanoberlato.PointerTypeString
import it.stefanoberlato.oabe.LibopenabeUtil.cloneDeallocAndReturn

actual class OpenABECryptoContextObject : Structure() {

    override fun getFieldOrder(): List<String> = listOf("obj")

    /**
     * This must be "var" (and not "val"), otherwise JNA will
     * throw an exception when instantiating the class a second
     * time (because val cannot be reassigned, I guess, even
     * though they should be two different instances)
     */
    @JvmField
    var obj: PointerByReference? = null
}

actual class OpenABECryptoContext actual constructor(
    schemeID: SchemeID,
    base64encode: Boolean
) {

    actual val context: OpenABECryptoContextObject =
        LibopenabeInitializer.openabeJna.openABECryptoContext_create(
            schemeID = schemeID.toString(),
            base64encode = base64encode
        )

    actual var publicParametersSetup: Boolean = false
    actual var secretParametersSetup: Boolean = false

    actual var destroyed: Boolean = false


    actual fun destroy() {
        checkPreconditions(checkParams = false)
        LibopenabeInitializer.openabeJna.openABECryptoContext_destroy(
            openABECryptoContextObject = context
        )
        context.clear()
        destroyed = true
    }

    actual fun generateParams() {
        checkPreconditions(checkParams = false)
        LibopenabeInitializer.openabeJna.openABECryptoContext_generateParams(
            openABECryptoContextObject = context
        )
        publicParametersSetup = true
        secretParametersSetup = true
    }

    actual fun keygen(
        keyInput: String,
        keyID: String,
        authID: String,
        GID: String
    ) {
        checkPreconditions()
        LibopenabeInitializer.openabeJna.openABECryptoContext_keygen(
            openABECryptoContextObject = context,
            keyInput = keyInput,
            keyID = keyID,
            authID = authID,
            GID = GID
        )
    }

    actual fun encrypt(
        encInput: String,
        plaintext: String
    ): String {
        checkPreconditions()
        val pointerToCiphertext = LibopenabeInitializer.openabeJna.openABECryptoContext_encrypt(
            openABECryptoContextObject = context,
            encInput = encInput,
            plaintext = plaintext
        )
        return cloneDeallocAndReturn(pointerToCiphertext)
    }

    actual fun decrypt(
        ciphertext: String
    ): String {
        checkPreconditions()
        val errorCode = IntArray(1)
        val pointerToPlaintext = LibopenabeInitializer.openabeJna.openABECryptoContext_decrypt(
            openABECryptoContextObject = context,
            ciphertext = ciphertext,
            errorCode = errorCode
        )
        val returnedValue = cloneDeallocAndReturn(pointerToPlaintext)
        return when (State.fromInt(errorCode[0])) {
            State.Success -> returnedValue
            State.ABEDecryptionError -> throw OpenABECryptoContextDecrypt(returnedValue)
            else -> throw OpenABECryptoContextRunTime()
        }
    }

    actual fun decrypt(
        keyID: String,
        ciphertext: String
    ): String {
        checkPreconditions()
        val errorCode = IntArray(1)
        val pointerToPlaintext = LibopenabeInitializer.openabeJna.openABECryptoContext_decryptWithKeyID(
            openABECryptoContextObject = context,
            keyID = keyID,
            ciphertext = ciphertext,
            errorCode = errorCode
        )
        val returnedValue = cloneDeallocAndReturn(pointerToPlaintext)
        return when (State.fromInt(errorCode[0])) {
            State.Success -> returnedValue
            State.ABEDecryptionError -> throw OpenABECryptoContextDecrypt(returnedValue)
            else -> throw OpenABECryptoContextRunTime()
        }
    }

    actual fun exportPublicParams(): String {
        checkPreconditions()
        val pointerToParams = LibopenabeInitializer.openabeJna.openABECryptoContext_exportPublicParams(
            openABECryptoContextObject = context
        )
        return cloneDeallocAndReturn(pointerToParams)
    }

    actual fun exportSecretParams(): String {
        checkPreconditions()
        val pointerToParams = LibopenabeInitializer.openabeJna.openABECryptoContext_exportSecretParams(
            openABECryptoContextObject = context
        )
        return cloneDeallocAndReturn(pointerToParams)
    }

    actual fun exportUserKey(
        keyID: String
    ): String {
        val errorCode = IntArray(1)
        checkPreconditions()
        val pointerToKey = LibopenabeInitializer.openabeJna.openABECryptoContext_exportUserKey(
            openABECryptoContextObject = context,
            keyID = keyID,
            errorCode = errorCode
        )
        val returnedValue = cloneDeallocAndReturn(pointerToKey)
        return when (State.fromInt(errorCode[0])) {
            State.Success -> returnedValue
            State.ABEExportKeyError -> throw OpenABECryptoContextExportKey(returnedValue)
            else -> throw OpenABECryptoContextRunTime()
        }
    }

    actual fun enableKeyManager(
        userId: String
    ) {
        checkPreconditions()
        LibopenabeInitializer.openabeJna.openABECryptoContext_enableKeyManager(
            openABECryptoContextObject = context,
            userId = userId
        )
    }

    actual fun enableVerbose() {
        checkPreconditions(checkParams = false)
        LibopenabeInitializer.openabeJna.openABECryptoContext_enableVerbose(
            openABECryptoContextObject = context
        )
    }

    actual fun importPublicParams(
        keyBlob: String
    ) {
        checkPreconditions(checkParams = false)
        LibopenabeInitializer.openabeJna.openABECryptoContext_importPublicParams(
            openABECryptoContextObject = context,
            keyBlob = keyBlob
        )
        publicParametersSetup = true
    }

    actual fun importSecretParams(
        keyBlob: String
    ) {
        checkPreconditions(checkParams = false)
        LibopenabeInitializer.openabeJna.openABECryptoContext_importSecretParams(
            openABECryptoContextObject = context,
            keyBlob = keyBlob
        )
        secretParametersSetup = true
    }

    actual fun importUserKey(
        keyID: String,
        keyBlob: String
    ) {
        checkPreconditions()
        LibopenabeInitializer.openabeJna.openABECryptoContext_importUserKey(
            openABECryptoContextObject = context,
            keyID = keyID,
            keyBlob = keyBlob
        )
    }

    actual fun deleteKey(
        keyID: String
    ): Boolean {
        checkPreconditions()
        val errorCode = IntArray(1)
        LibopenabeInitializer.openabeJna.openABECryptoContext_deleteKey(
            openABECryptoContextObject = context,
            keyID = keyID,
            errorCode = errorCode
        )
        when (State.fromInt(errorCode[0])) {
            State.Success -> { }
            State.ABEDeleteKeyError -> throw OpenABECryptoContextKeyDelete()
            else -> throw OpenABECryptoContextRunTime()
        }
        return true
    }



    private fun checkPreconditions(
        checkDestroyed: Boolean = true,
        checkParams: Boolean = true
    ) {
        if (checkDestroyed && destroyed) {
            throw OpenABECryptoContextDestroyed()
        }
        if (checkParams && (!publicParametersSetup || !secretParametersSetup)) {
            throw OpenABECryptoContextMissingParameters()
        }
    }
}
