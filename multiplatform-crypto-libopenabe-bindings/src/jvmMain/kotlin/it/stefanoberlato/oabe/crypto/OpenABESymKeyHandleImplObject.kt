package it.stefanoberlato.oabe.crypto

import com.sun.jna.Structure
import com.sun.jna.ptr.PointerByReference
import it.stefanoberlato.LibopenabeInitializer
import it.stefanoberlato.PointerTypeString
import it.stefanoberlato.oabe.State

actual class OpenABESymKeyHandleImplObject : Structure() {

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

actual class OpenABESymKeyHandleImpl actual constructor(
    keyBytes: ByteArray,
    applyB64Encode: Boolean
) {

    actual val context: OpenABESymKeyHandleImplObject =
        LibopenabeInitializer.openabeJna.openABESymKeyHandleImpl_create(
            keyBytes = keyBytes,
            keyBytesLen = keyBytes.size,
            apply_b64_encode = applyB64Encode
        )

    private fun cloneDeallocAndReturn(pointer: PointerTypeString) : String {
        val clonedString = "" + pointer.string
        pointer.free()
        return clonedString
    }


    actual fun encrypt(
        plaintext: String
    ): String {
        val errorCode = IntArray(1)
        val pointerToCiphertext = LibopenabeInitializer.openabeJna.openABESymKeyHandleImpl_encrypt(
            openABESymKeyHandleImplObject = context,
            plaintext = plaintext,
            errorCode = errorCode
        )
        val returnedValue = cloneDeallocAndReturn(pointerToCiphertext)
        return when (State.fromInt(errorCode[0])) {
            State.Success -> returnedValue
            State.SymEncryptionError -> throw OpenABESymKeyHandleImplEncrypt(returnedValue)
            else -> throw OpenABESymKeyHandleImplRunTime()
        }
    }

    actual fun decrypt(
        ciphertext: String
    ): String {
        val errorCode = IntArray(1)
        val pointerToPlaintext = LibopenabeInitializer.openabeJna.openABESymKeyHandleImpl_decrypt(
            openABESymKeyHandleImplObject = context,
            ciphertext = ciphertext,
            errorCode = errorCode
        )
        val returnedValue = cloneDeallocAndReturn(pointerToPlaintext)
        return when (State.fromInt(errorCode[0])) {
            State.Success -> returnedValue
            State.SymDecryptionError -> throw OpenABESymKeyHandleImplDecrypt(returnedValue)
            else -> throw OpenABESymKeyHandleImplRunTime()
        }
    }

    actual fun exportRawKey(): String {
        val pointerToKey = LibopenabeInitializer.openabeJna.openABESymKeyHandleImpl_exportRawKey(
            openABESymKeyHandleImplObject = context,
        )
        return cloneDeallocAndReturn(pointerToKey)
    }

    actual fun exportKey(): String {
        val pointerToKey = LibopenabeInitializer.openabeJna.openABESymKeyHandleImpl_exportKey(
            openABESymKeyHandleImplObject = context,
        )
        return cloneDeallocAndReturn(pointerToKey)
    }
}
