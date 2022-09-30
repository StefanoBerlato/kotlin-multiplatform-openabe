package it.stefanoberlato.oabe

import it.stefanoberlato.oabe.LibopenabeUtil.cloneDeallocAndReturn
import kotlinx.cinterop.*

actual typealias OpenABECryptoContextObject = cnames.structs.openABECryptoContext

actual class OpenABECryptoContext actual constructor(
    schemeID: SchemeID,
    base64encode: Boolean
) {
    
    actual val context: OpenABECryptoContextObject =
        libwrapper.openABECryptoContext_create(
            schemeID = schemeID.toString(),
            base64encode = base64encode
        )!!.pointed

    actual var publicParametersSetup: Boolean = false
    actual var secretParametersSetup: Boolean = false

    actual var destroyed: Boolean = false

    actual fun destroy() {
        checkPreconditions(checkParams = false)
        libwrapper.openABECryptoContext_destroy(
            m = context.ptr
        )
        destroyed = true
    }

    actual fun generateParams() {
        checkPreconditions(checkParams = false)
        libwrapper.openABECryptoContext_generateParams(
            m = context.ptr
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
        libwrapper.openABECryptoContext_keygen(
            m = context.ptr,
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
        val pointerToCiphertext = libwrapper.openABECryptoContext_encrypt(
            m = context.ptr,
            encInput = encInput,
            plaintext = plaintext
        )
        return cloneDeallocAndReturn(pointerToCiphertext!!)
    }

    actual fun decrypt(
        ciphertext: String
    ): String {
        checkPreconditions()
        return IntArray(1).usePinned { pinned ->
            val pointerToPlaintext = libwrapper.openABECryptoContext_decrypt(
                m = context.ptr,
                ciphertext = ciphertext,
                errorCode = pinned.addressOf(0)
            )
            val returnedValue = cloneDeallocAndReturn(pointerToPlaintext!!)
            when (State.fromInt(pinned.get()[0])) {
                State.Success -> returnedValue
                State.ABEDecryptionError -> throw OpenABECryptoContextDecrypt(returnedValue)
                else -> throw OpenABECryptoContextRunTime()
            }
        }
    }

    actual fun decrypt(
        keyID: String,
        ciphertext: String
    ): String {
        checkPreconditions()
        return IntArray(1).usePinned { pinned ->
            val pointerToPlaintext = libwrapper.openABECryptoContext_decryptWithKeyID(
                m = context.ptr,
                keyID = keyID,
                ciphertext = ciphertext,
                errorCode = pinned.addressOf(0)
            )
            val returnedValue = cloneDeallocAndReturn(pointerToPlaintext!!)
            when (State.fromInt(pinned.get()[0])) {
                State.Success -> returnedValue
                State.ABEDecryptionError -> throw OpenABECryptoContextDecrypt(returnedValue)
                else -> throw OpenABECryptoContextRunTime()
            }
        }
    }

    actual fun exportPublicParams(): String {
        checkPreconditions()
        val pointerToParams = libwrapper.openABECryptoContext_exportPublicParams(
            m = context.ptr
        )
        return cloneDeallocAndReturn(pointerToParams!!)
    }

    actual fun exportSecretParams(): String {
        checkPreconditions()
        val pointerToParams = libwrapper.openABECryptoContext_exportSecretParams(
            m = context.ptr
        )
        return cloneDeallocAndReturn(pointerToParams!!)
    }

    actual fun exportUserKey(
        keyID: String
    ): String {
        checkPreconditions()
        return IntArray(1).usePinned { pinned ->
            val pointerToKey = libwrapper.openABECryptoContext_exportUserKey(
                m = context.ptr,
                keyID = keyID,
                errorCode = pinned.addressOf(0)
            )
            val returnedValue = cloneDeallocAndReturn(pointerToKey!!)
            when (State.fromInt(pinned.get()[0])) {
                State.Success -> returnedValue
                State.ABEExportKeyError -> throw OpenABECryptoContextExportKey(returnedValue)
                else -> throw OpenABECryptoContextRunTime()
            }
        }
    }

    actual fun enableKeyManager(
        userID: String
    ) {
        checkPreconditions()
        libwrapper.openABECryptoContext_enableKeyManager(
            m = context.ptr,
            userId = userID
        )
    }

    actual fun enableVerbose() {
        checkPreconditions(checkParams = false)
        libwrapper.openABECryptoContext_enableVerbose(
            m = context.ptr
        )
    }

    actual fun importPublicParams(
        keyBlob: String
    ) {
        checkPreconditions(checkParams = false)
        libwrapper.openABECryptoContext_importPublicParams(
            m = context.ptr,
            keyBlob = keyBlob
        )
        publicParametersSetup = true
    }

    actual fun importSecretParams(
        keyBlob: String
    ) {
        checkPreconditions(checkParams = false)
        libwrapper.openABECryptoContext_importSecretParams(
            m = context.ptr,
            keyBlob = keyBlob
        )
        secretParametersSetup = true
    }

    actual fun importUserKey(
        keyID: String,
        keyBlob: String
    ) {
        checkPreconditions()
        libwrapper.openABECryptoContext_importUserKey(
            m = context.ptr,
            keyID = keyID,
            keyBlob = keyBlob
        )
    }

    actual fun deleteKey(
        keyID: String
    ): Boolean {
        checkPreconditions()
        IntArray(1).usePinned { pinned ->
            libwrapper.openABECryptoContext_deleteKey(
                m = context.ptr,
                keyID = keyID,
                errorCode = pinned.addressOf(0)
            )
            when (State.fromInt(pinned.get()[0])) {
                State.Success -> { }
                State.ABEDeleteKeyError -> throw OpenABECryptoContextKeyDelete()
                else -> throw OpenABECryptoContextRunTime()
            }
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