package it.stefanoberlato.oabe

import it.stefanoberlato.oabe.LibopenabeUtil.cloneDeallocAndReturn
import kotlinx.cinterop.*

actual typealias OpenPKEContextObject = cnames.structs.openPKEContext

actual class OpenPKEContext actual constructor(
    ecID: ECID,
    base64encode: Boolean
) {

    actual val context: OpenPKEContextObject =
        libwrapper.openPKEContext_create(
            ecID = ecID.toString(),
            base64encode = base64encode
        )!!.pointed

    actual var destroyed: Boolean = false



    actual fun destroy() {
        checkPreconditions()
        libwrapper.openPKEContext_destroy(
            m = context.ptr
        )
        destroyed = true
    }

    actual fun keygen(
        keyID: String
    ) {
        checkPreconditions()
        libwrapper.openPKEContext_keygen(
            m = context.ptr,
            keyID = keyID,
        )
    }

    actual fun encrypt(
        receiverID: String,
        plaintext: String,
    ): String {
        checkPreconditions()
        return IntArray(1).usePinned { pinned ->
            val pointerToCiphertext = libwrapper.openPKEContext_encrypt(
                m = context.ptr,
                receiverID = receiverID,
                plaintext = plaintext,
                errorCode = pinned.addressOf(0)
            )
            val returnedValue = cloneDeallocAndReturn(pointerToCiphertext!!)
            when (State.fromInt(pinned.get()[0])) {
                State.Success -> returnedValue
                State.PKEEncryptionError -> throw OpenPKEContextEncrypt(returnedValue)
                else -> throw OpenPKEContextRunTime()
            }
        }
    }

    actual fun decrypt(
        receiverID: String,
        ciphertext: String,
    ): String {
        checkPreconditions()
        return IntArray(1).usePinned { pinned ->
            val pointerToPlaintext = libwrapper.openPKEContext_decrypt(
                m = context.ptr,
                receiverID = receiverID,
                ciphertext = ciphertext,
                errorCode = pinned.addressOf(0)
            )
            val returnedValue = cloneDeallocAndReturn(pointerToPlaintext!!)
            when (State.fromInt(pinned.get()[0])) {
                State.Success -> returnedValue
                State.PKEDecryptionError -> throw OpenPKEContextDecrypt(returnedValue)
                else -> throw OpenPKEContextRunTime()
            }
        }
    }

    actual fun exportPublicKey(
        keyID: String
    ): String {
        checkPreconditions()
        return IntArray(1).usePinned { pinned ->
            val pointerToKey = libwrapper.openPKEContext_exportPublicKey(
                m = context.ptr,
                keyID = keyID,
                errorCode = pinned.addressOf(0)
            )
            val returnedValue = cloneDeallocAndReturn(pointerToKey!!)
            when (State.fromInt(pinned.get()[0])) {
                State.Success -> returnedValue
                State.PKEExportKeyError -> throw OpenPKEContextExportKey(returnedValue)
                else -> throw OpenPKEContextRunTime()
            }
        }
    }

    actual fun exportPrivateKey(
        keyID: String
    ): String {
        checkPreconditions()
        return IntArray(1).usePinned { pinned ->
            val pointerToKey = libwrapper.openPKEContext_exportPublicKey(
                m = context.ptr,
                keyID = keyID,
                errorCode = pinned.addressOf(0)
            )
            val returnedValue = cloneDeallocAndReturn(pointerToKey!!)
            when (State.fromInt(pinned.get()[0])) {
                State.Success -> returnedValue
                State.PKEExportKeyError -> throw OpenPKEContextExportKey(returnedValue)
                else -> throw OpenPKEContextRunTime()
            }
        }
    }

    actual fun importPublicKey(
        keyID: String,
        keyBlob: String
    ) {
        checkPreconditions()
        libwrapper.openPKEContext_importPublicKey(
            m = context.ptr,
            keyID = keyID,
            keyBlob = keyBlob
        )
    }
    actual fun importPrivateKey(
        keyID: String,
        keyBlob: String
    ) {
        checkPreconditions()
        libwrapper.openPKEContext_importPrivateKey(
            m = context.ptr,
            keyID = keyID,
            keyBlob = keyBlob
        )
    }

    private fun checkPreconditions(
        checkDestroyed: Boolean = true
    ) {
        if (checkDestroyed && destroyed) {
            throw OpenPKEContextDestroyed()
        }
    }
}