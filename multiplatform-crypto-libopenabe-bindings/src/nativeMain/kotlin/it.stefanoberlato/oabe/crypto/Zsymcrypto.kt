package it.stefanoberlato.oabe.crypto

import it.stefanoberlato.oabe.LibopenabeUtil.cloneDeallocAndReturn
import kotlinx.cinterop.readBytes
import kotlinx.cinterop.toCValues

actual object Zsymcrypto {

    actual fun generateSymmetricKey(keyLen: Int): ByteArray {
        val pointerToKey = libwrapper.zsymcrypto_generateSymmetricKey(
            keyLen = keyLen,
        )
        val keyArray = pointerToKey!!.readBytes(keyLen)
        libwrapper.freeString(pointerToKey)
        return keyArray
    }

    actual fun printAsHex(binBuf: String): String {
        val pointerToKeyAsHex = libwrapper.zsymcrypto_printAsHex(
            binBuf = binBuf,
        )
        return cloneDeallocAndReturn(pointerToKeyAsHex!!)
    }
}