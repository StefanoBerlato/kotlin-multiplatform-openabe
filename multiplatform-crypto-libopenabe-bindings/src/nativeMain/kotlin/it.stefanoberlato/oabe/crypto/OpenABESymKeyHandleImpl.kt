package it.stefanoberlato.oabe.crypto

import it.stefanoberlato.oabe.LibopenabeUtil.cloneDeallocAndReturn
import it.stefanoberlato.oabe.State
import kotlinx.cinterop.*

actual typealias OpenABESymKeyHandleImplObject = cnames.structs.openABESymKeyHandleImpl

actual class OpenABESymKeyHandleImpl actual constructor(
    keyBytes: ByteArray,
    applyB64Encode: Boolean
) {

    actual val context: OpenABESymKeyHandleImplObject =
        libwrapper.openABESymKeyHandleImpl_create(
            keyBytes = keyBytes.toCValues(),
            keyBytesLen = keyBytes.size,
            apply_b64_encode = applyB64Encode
        )!!.pointed

    actual var destroyed: Boolean = false

    actual fun destroy() {
        checkPreconditions()
        libwrapper.openABESymKeyHandleImpl_destroy(
            m = context.ptr
        )
        destroyed = true
    }

    actual fun encrypt(
        plaintext: String,
    ): String {
        checkPreconditions()
        return IntArray(1).usePinned { pinned ->
            val pointerToCiphertext = libwrapper.openABESymKeyHandleImpl_encrypt(
                m = context.ptr,
                plaintext = plaintext,
                errorCode = pinned.addressOf(0)
            )
            val returnedValue = cloneDeallocAndReturn(pointerToCiphertext!!)
            when (State.fromInt(pinned.get()[0])) {
                State.Success -> returnedValue
                State.SymEncryptionError -> throw OpenABESymKeyHandleImplEncrypt(returnedValue)
                else -> throw OpenABESymKeyHandleImplRunTime()
            }
        }
    }

    actual fun decrypt(
        ciphertext: String,
    ): String {
        checkPreconditions()
        return IntArray(1).usePinned { pinned ->
            val pointerToPlaintext = libwrapper.openABESymKeyHandleImpl_decrypt(
                m = context.ptr,
                ciphertext = ciphertext,
                errorCode = pinned.addressOf(0)
            )
            val returnedValue = cloneDeallocAndReturn(pointerToPlaintext!!)
            when (State.fromInt(pinned.get()[0])) {
                State.Success -> returnedValue
                State.SymDecryptionError -> throw OpenABESymKeyHandleImplDecrypt(returnedValue)
                else -> throw OpenABESymKeyHandleImplRunTime()
            }
        }
    }

    actual fun exportRawKey(): String {
        checkPreconditions()
        val pointerToKey = libwrapper.openABESymKeyHandleImpl_exportRawKey(
            m = context.ptr,
        )
        return cloneDeallocAndReturn(pointerToKey!!)
    }

    actual fun exportKey(): String {
        checkPreconditions()
        val pointerToKey = libwrapper.openABESymKeyHandleImpl_exportKey(
            m = context.ptr,
        )
        return cloneDeallocAndReturn(pointerToKey!!)
    }

    private fun checkPreconditions(
        checkDestroyed: Boolean = true
    ) {
        if (checkDestroyed && destroyed) {
            throw OpenABESymKeyHandleImplDestroyed()
        }
    }
}