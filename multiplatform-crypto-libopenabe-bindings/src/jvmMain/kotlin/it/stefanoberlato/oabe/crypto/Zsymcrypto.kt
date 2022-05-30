package it.stefanoberlato.oabe.crypto

import it.stefanoberlato.LibopenabeInitializer
import it.stefanoberlato.oabe.LibopenabeUtil.cloneDeallocAndReturn

actual object Zsymcrypto {

    actual fun generateSymmetricKey(keyLen: Int): ByteArray {
        val pointerToKey = LibopenabeInitializer.openabeJna.zsymcrypto_generateSymmetricKey(
            keyLen = keyLen,
        )
        val keyArray = pointerToKey.pointer.getByteArray(0, keyLen)
        pointerToKey.free()
        return keyArray
    }

    actual fun printAsHex(binBuf: String): String {
        val pointerToKeyAsHex = LibopenabeInitializer.openabeJna.zsymcrypto_printAsHex(
            binBuf = binBuf,
        )
        return cloneDeallocAndReturn(pointerToKeyAsHex)
    }

    // TODO delete
    actual fun getLength(a: ByteArray): Int {
        return LibopenabeInitializer.openabeJna.getLength(a)
    }
    // TODO delete
}