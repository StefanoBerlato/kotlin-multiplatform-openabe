package it.stefanoberlato.oabe

import com.sun.jna.Structure
import com.sun.jna.ptr.PointerByReference
import it.stefanoberlato.LibopenabeInitializer
import it.stefanoberlato.oabe.LibopenabeUtil.cloneDeallocAndReturn

actual class OpenPKSIGContextObject : Structure() {

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

actual class OpenPKSIGContext actual constructor(
    ecID: ECID,
    base64encode: Boolean
) {

    actual val context: OpenPKSIGContextObject =
        LibopenabeInitializer.openabeJna.openPKSIGContext_create(
            ecID = ecID.toString(),
            base64encode = base64encode
        )

    actual var destroyed: Boolean = false


    actual fun destroy() {
        checkPreconditions()
        LibopenabeInitializer.openabeJna.openPKSIGContext_destroy(
            openPKSIGContextObject = context
        )
        context.clear()
        destroyed = true
    }

    actual fun keygen(
        keyID: String
    ) {
        checkPreconditions()
        LibopenabeInitializer.openabeJna.openPKSIGContext_keygen(
            openPKSIGContextObject = context,
            keyID = keyID,
        )
    }

    actual fun sign(
        keyID: String,
        message: String
    ): String {
        checkPreconditions()
        val errorCode = IntArray(1)
        val pointerToSignature = LibopenabeInitializer.openabeJna.openPKSIGContext_sign(
            openPKSIGContextObject = context,
            keyID = keyID,
            message = message,
            errorCode = errorCode
        )
        val returnedValue = cloneDeallocAndReturn(pointerToSignature)
        return when (State.fromInt(errorCode[0])) {
            State.Success -> returnedValue
            State.PKSIGSignError -> throw OpenPKSIGContextSign(returnedValue)
            else -> throw OpenPKSIGContextRunTime()
        }
    }

    actual fun verify(
        keyID: String,
        message: String,
        signature: String
    ) {
        checkPreconditions()
        val errorCode = IntArray(1)
        val pointerToPlaintext = LibopenabeInitializer.openabeJna.openPKSIGContext_verify(
            openPKSIGContextObject = context,
            keyID = keyID,
            message = message,
            signature = signature,
            errorCode = errorCode
        )
        val returnedValue = cloneDeallocAndReturn(pointerToPlaintext)
        when (State.fromInt(errorCode[0])) {
            State.Success -> { }
            State.PKSIGVerifyError -> throw OpenPKSIGContextVerify(returnedValue)
            else -> throw OpenPKSIGContextRunTime()
        }
    }

    actual fun exportPublicKey(
        keyID: String
    ): String {
        checkPreconditions()
        val errorCode = IntArray(1)
        val pointerToKey = LibopenabeInitializer.openabeJna.openPKSIGContext_exportPublicKey(
            openPKSIGContextObject = context,
            keyID = keyID,
            errorCode = errorCode
        )
        val returnedValue = cloneDeallocAndReturn(pointerToKey)
        return when (State.fromInt(errorCode[0])) {
            State.Success -> returnedValue
            State.PKSIGExportKeyError -> throw OpenPKSIGContextExportKey(returnedValue)
            else -> throw OpenPKSIGContextRunTime()
        }
    }

    actual fun exportPrivateKey(
        keyID: String
    ): String {
        checkPreconditions()
        val errorCode = IntArray(1)
        val pointerToKey = LibopenabeInitializer.openabeJna.openPKSIGContext_exportPrivateKey(
            openPKSIGContextObject = context,
            keyID = keyID,
            errorCode = errorCode
        )
        val returnedValue = cloneDeallocAndReturn(pointerToKey)
        return when (State.fromInt(errorCode[0])) {
            State.Success -> returnedValue
            State.PKSIGExportKeyError -> throw OpenPKSIGContextExportKey(returnedValue)
            else -> throw OpenPKSIGContextRunTime()
        }
    }

    actual fun importPublicKey(
        keyID: String,
        keyBlob: String
    ) {
        checkPreconditions()
        LibopenabeInitializer.openabeJna.openPKSIGContext_importPublicKey(
            openPKSIGContextObject = context,
            keyID = keyID,
            keyBlob = keyBlob
        )
    }

    actual fun importPrivateKey(
        keyID: String,
        keyBlob: String
    ) {
        checkPreconditions()
        LibopenabeInitializer.openabeJna.openPKSIGContext_importPrivateKey(
            openPKSIGContextObject = context,
            keyID = keyID,
            keyBlob = keyBlob
        )
    }

    private fun checkPreconditions(
        checkDestroyed: Boolean = true,
    ) {
        if (checkDestroyed && destroyed) {
            throw OpenPKSIGContextDestroyed()
        }
    }
}
