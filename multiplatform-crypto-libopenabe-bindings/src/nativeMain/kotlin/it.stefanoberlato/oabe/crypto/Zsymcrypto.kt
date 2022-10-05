package it.stefanoberlato.oabe.crypto

import it.stefanoberlato.oabe.LibopenabeUtil.freeAndReturn
import kotlinx.cinterop.readBytes

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
        return freeAndReturn(pointerToKeyAsHex!!)
    }
}