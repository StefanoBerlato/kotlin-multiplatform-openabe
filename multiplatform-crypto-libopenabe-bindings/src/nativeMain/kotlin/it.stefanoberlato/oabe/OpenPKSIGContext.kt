package it.stefanoberlato.oabe

import it.stefanoberlato.oabe.LibopenabeUtil.cloneDeallocAndReturn
import kotlinx.cinterop.*

actual typealias OpenPKSIGContextObject = cnames.structs.openPKSIGContext

actual class OpenPKSIGContext actual constructor(
    ecID: ECID,
    base64encode: Boolean
) {

    actual val context: OpenPKSIGContextObject =
        libwrapper.openPKSIGContext_create(
            ecID = ecID.toString(),
            base64encode = base64encode
        )!!.pointed

    actual var destroyed: Boolean = false

    actual fun destroy() {
        checkPreconditions()
        libwrapper.openPKSIGContext_destroy(
            m = context.ptr
        )
        destroyed = true
    }

    actual fun keygen(
        keyID: String
    ) {
        checkPreconditions()
        libwrapper.openPKSIGContext_keygen(
            m = context.ptr,
            keyID = keyID
        )
    }

    actual fun sign(
        keyID: String,
        message: String
    ): String {
        checkPreconditions()
        return IntArray(1).usePinned { pinned ->
            val pointerToSignature = libwrapper.openPKSIGContext_sign(
                m = context.ptr,
                keyID = keyID,
                message = message,
                errorCode = pinned.addressOf(0)
            )
            val returnedValue = cloneDeallocAndReturn(pointerToSignature!!)
            when (State.fromInt(pinned.get()[0])) {
                State.Success -> returnedValue
                State.PKSIGSignError -> throw OpenPKSIGContextSign(returnedValue)
                else -> throw OpenPKSIGContextRunTime()
            }
        }
    }

    actual fun verify(
        keyID: String,
        message: String,
        signature: String
    ) {
        checkPreconditions()
        return IntArray(1).usePinned { pinned ->
            val pointerToMessage = libwrapper.openPKSIGContext_verify(
                m = context.ptr,
                keyID = keyID,
                message = message,
                signature = signature,
                errorCode = pinned.addressOf(0)
            )
            val returnedValue = cloneDeallocAndReturn(pointerToMessage!!)
            when (State.fromInt(pinned.get()[0])) {
                State.Success -> { }
                State.PKSIGVerifyError -> throw OpenPKSIGContextVerify(returnedValue)
                else -> throw OpenPKSIGContextRunTime()
            }
        }
    }

    actual fun exportPublicKey(
        keyID: String
    ): String {
        checkPreconditions()
        return IntArray(1).usePinned { pinned ->
            val pointerToKey = libwrapper.openPKSIGContext_exportPublicKey(
                m = context.ptr,
                keyID = keyID,
                errorCode = pinned.addressOf(0)
            )
            val returnedValue = cloneDeallocAndReturn(pointerToKey!!)
            when (State.fromInt(pinned.get()[0])) {
                State.Success -> returnedValue
                State.PKSIGExportKeyError -> throw OpenPKSIGContextExportKey(returnedValue)
                else -> throw OpenPKSIGContextRunTime()
            }
        }
    }

    actual fun exportPrivateKey(
        keyID: String
    ): String {
        checkPreconditions()
        return IntArray(1).usePinned { pinned ->
            val pointerToKey = libwrapper.openPKSIGContext_exportPrivateKey(
                m = context.ptr,
                keyID = keyID,
                errorCode = pinned.addressOf(0)
            )
            val returnedValue = cloneDeallocAndReturn(pointerToKey!!)
            when (State.fromInt(pinned.get()[0])) {
                State.Success -> returnedValue
                State.PKSIGExportKeyError -> throw OpenPKSIGContextExportKey(returnedValue)
                else -> throw OpenPKSIGContextRunTime()
            }
        }
    }

    actual fun importPublicKey(
        keyID: String,
        keyBlob: String
    ) {
        checkPreconditions()
        libwrapper.openPKSIGContext_importPublicKey(
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
        libwrapper.openPKSIGContext_importPrivateKey(
            m = context.ptr,
            keyID = keyID,
            keyBlob = keyBlob
        )
    }

    private fun checkPreconditions(
        checkDestroyed: Boolean = true
    ) {
        if (checkDestroyed && destroyed) {
            throw OpenPKSIGContextDestroyed()
        }
    }
}