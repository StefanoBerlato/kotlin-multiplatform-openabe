package it.stefanoberlato.oabe

import com.sun.jna.Structure
import com.sun.jna.ptr.PointerByReference
import it.stefanoberlato.LibopenabeInitializer
import it.stefanoberlato.oabe.LibopenabeUtil.cloneDeallocAndReturn

actual class OpenPKEContextObject : Structure() {

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

actual class OpenPKEContext actual constructor(
    ecID: ECID, 
    base64encode: Boolean
) {

    actual val context: OpenPKEContextObject =
        LibopenabeInitializer.openabeJna.openPKEContext_create(
            ecID = ecID.toString(),
            base64encode = base64encode
        )

    actual var destroyed: Boolean = false


    actual fun destroy() {
        checkPreconditions()
        LibopenabeInitializer.openabeJna.openPKEContext_destroy(
            openPKEContextObject = context
        )
        context.clear()
        destroyed = true
    }

    actual fun keygen(
        keyID: String
    ) {
        checkPreconditions()
        LibopenabeInitializer.openabeJna.openPKEContext_keygen(
            openPKEContextObject = context,
            keyID = keyID,
        )
    }

    actual fun encrypt(
        receiverID: String,
        plaintext: String,
    ): String {
        checkPreconditions()
        val errorCode = IntArray(1)
        val pointerToCiphertext = LibopenabeInitializer.openabeJna.openPKEContext_encrypt(
            openPKEContextObject = context,
            receiverID = receiverID,
            plaintext = plaintext,
            errorCode = errorCode
        )
        val returnedValue = cloneDeallocAndReturn(pointerToCiphertext)
        return when (State.fromInt(errorCode[0])) {
            State.Success -> returnedValue
            State.PKEEncryptionError -> throw OpenPKEContextEncrypt(returnedValue)
            else -> throw OpenPKEContextRunTime()
        }
    }

    actual fun decrypt(
        receiverID: String,
        ciphertext: String,
    ): String {
        checkPreconditions()
        val errorCode = IntArray(1)
        val pointerToPlaintext = LibopenabeInitializer.openabeJna.openPKEContext_decrypt(
            openPKEContextObject = context,
            receiverID = receiverID,
            ciphertext = ciphertext,
            errorCode = errorCode
        )
        val returnedValue = cloneDeallocAndReturn(pointerToPlaintext)
        return when (State.fromInt(errorCode[0])) {
            State.Success -> returnedValue
            State.PKEDecryptionError -> throw OpenPKEContextDecrypt(returnedValue)
            else -> throw OpenPKEContextRunTime()
        }
    }

    actual fun exportPublicKey(
        keyID: String
    ): String {
        checkPreconditions()
        val errorCode = IntArray(1)
        val pointerToKey = LibopenabeInitializer.openabeJna.openPKEContext_exportPublicKey(
            openPKEContextObject = context,
            keyID = keyID,
            errorCode = errorCode
        )
        val returnedValue = cloneDeallocAndReturn(pointerToKey)
        return when (State.fromInt(errorCode[0])) {
            State.Success -> returnedValue
            State.PKEExportKeyError -> throw OpenPKEContextExportKey(returnedValue)
            else -> throw OpenPKEContextRunTime()
        }
    }

    actual fun exportPrivateKey(
        keyID: String
    ): String {
        checkPreconditions()
        val errorCode = IntArray(1)
        val pointerToKey = LibopenabeInitializer.openabeJna.openPKEContext_exportPrivateKey(
            openPKEContextObject = context,
            keyID = keyID,
            errorCode = errorCode
        )
        val returnedValue = cloneDeallocAndReturn(pointerToKey)
        return when (State.fromInt(errorCode[0])) {
            State.Success -> returnedValue
            State.PKEExportKeyError -> throw OpenPKEContextExportKey(returnedValue)
            else -> throw OpenPKEContextRunTime()
        }
    }

    actual fun importPublicKey(
        keyID: String,
        keyBlob: String
    ) {
        checkPreconditions()
        LibopenabeInitializer.openabeJna.openPKEContext_importPublicKey(
            openPKEContextObject = context,
            keyID = keyID,
            keyBlob = keyBlob
        )
    }

    actual fun importPrivateKey(
        keyID: String,
        keyBlob: String
    ) {
        checkPreconditions()
        LibopenabeInitializer.openabeJna.openPKEContext_importPrivateKey(
            openPKEContextObject = context,
            keyID = keyID,
            keyBlob = keyBlob
        )
    }


    private fun checkPreconditions(
        checkDestroyed: Boolean = true,
    ) {
        if (checkDestroyed && destroyed) {
            throw OpenPKEContextDestroyed()
        }
    }
}
