package it.stefanoberlato.oabe

import it.stefanoberlato.LibopenabeInitializer
import it.stefanoberlato.testBlocking
import kotlin.random.Random
import kotlin.test.*

class OpenABECryptoContextTest {

    private val myPlaintext = "Hello! I'm using Kotlin Multiplatform OpenABE."

    private var oabe: OpenABECryptoContext? = null

    @BeforeTest
    fun initializeLibraryAndContext() {
        testBlocking {
            LibopenabeInitializer.initialize()
        }
        oabe = OpenABECryptoContext(SchemeID.CP_ABE)
        oabe!!.enableVerbose()
        oabe!!.generateParams()
    }

    @AfterTest
    fun tearDown() {
        oabe!!.destroy()
        testBlocking {
            LibopenabeInitializer.shutdown()
        }
    }

    @Test
    fun `invoke any function after destroy fails`() {
        val newOabe = OpenABECryptoContext(SchemeID.CP_ABE)
        newOabe.destroy()
        var thrown = false
        try {
            newOabe.keygen("|attr1|attr2", "key0")
        } catch (e: OpenABECryptoContextDestroyed) {
            thrown = true
        }
        assertTrue(thrown)
    }

    @Test
    fun `invoke any function before generating parameters fails`() {
        val newOABE = OpenABECryptoContext(SchemeID.CP_ABE)
        var thrown = false
        try {
            newOABE.keygen("|attr1|attr2", "key0")
        } catch (e: OpenABECryptoContextMissingParameters) {
            thrown = true
        }
        newOABE.destroy()
        assertTrue(thrown)
    }

    @Test
    fun `encrypt and decrypt with authorized key works`() {
        oabe!!.keygen("|attr1|attr2", "key0")
        val plaintext = myPlaintext
        val ciphertext = oabe!!.encrypt("attr1 and attr2", plaintext)
        val decrypted = oabe!!.decrypt("key0", ciphertext)
        assertEquals(plaintext, decrypted)
    }

    @Test
    fun `decrypt with unauthorized key fails`() {
        oabe!!.keygen("|attr1", "key0")

        val plaintext = myPlaintext
        val ciphertext = oabe!!.encrypt("attr1 and attr2", plaintext)

        var thrown = false
        try {
            oabe!!.decrypt("key0", ciphertext)
        } catch (e: OpenABECryptoContextDecrypt) {
            thrown = true
        }
        assertTrue(thrown)
    }

    @Test
    fun `encrypt and decrypt with non existing key fails`() {
        oabe!!.keygen("|attr1|attr2", "key0")

        val plaintext = myPlaintext
        val ciphertext = oabe!!.encrypt("attr1 and attr2", plaintext)

        var thrown = false
        try {
            oabe!!.decrypt("key1", ciphertext)
        } catch (e: OpenABECryptoContextDecrypt) {
            thrown = true
        }
        assertTrue(thrown)
    }

    @Test
    fun `decrypt tampered ciphertext with authorized key fails`() {
        oabe!!.keygen("|attr1|attr2", "key0")

        val plaintext = myPlaintext
        val ciphertext = oabe!!.encrypt("attr1 and attr2", plaintext)
        val ciphertextByteArray = ciphertext.encodeToByteArray()

        var index1: Int
        var index2: Int
        do {
            index1 = Random.nextInt(0, ciphertextByteArray.size)
            index2 = Random.nextInt(0, ciphertextByteArray.size)
        } while (ciphertextByteArray[index1] == ciphertextByteArray[index2])
        ciphertextByteArray[index1] = ciphertextByteArray[index2].also {
            ciphertextByteArray[index2] = ciphertextByteArray[index1]
        }

        var thrown = false
        try {
            oabe!!.decrypt("key0", ciphertextByteArray.decodeToString())
        } catch (e: OpenABECryptoContextDecrypt) {
            thrown = true
        }
        assertTrue(thrown)
    }

    @Test
    fun `enable key manager and import key and decrypt with no key ID works`() {
        oabe!!.enableKeyManager("user1")
        oabe!!.keygen("|attr1|attr2", "key0")

        val plaintext = myPlaintext
        val ciphertext = oabe!!.encrypt("attr1 and attr2", plaintext)

        val keyBlob = oabe!!.exportUserKey("key0")
        oabe!!.importUserKey("key0", keyBlob)

        val decrypted = oabe!!.decrypt(ciphertext)
        assertEquals(plaintext, decrypted)
    }

    @Test
    fun `decrypt with no key ID without enabling the key manager first fails`() {
        oabe!!.keygen("|attr1|attr2", "key0")

        val plaintext = myPlaintext
        val ciphertext = oabe!!.encrypt("attr1 and attr2", plaintext)

        val keyBlob = oabe!!.exportUserKey("key0")
        oabe!!.importUserKey("key0", keyBlob)

        var thrown = false
        try {
            oabe!!.decrypt(ciphertext)
        } catch (e: OpenABECryptoContextDecrypt) {
            thrown = true
        }
        assertTrue(thrown)
    }

    @Test
    fun `enable key manager and not import key and decrypt with no key ID fails`() {
        oabe!!.enableKeyManager("user1")
        oabe!!.keygen("|attr1|attr2", "key0")

        val plaintext = myPlaintext
        val ciphertext = oabe!!.encrypt("attr1 and attr2", plaintext)

        var thrown = false
        try {
            oabe!!.decrypt(ciphertext)
        } catch (e: OpenABECryptoContextDecrypt) {
            thrown = true
        }
        assertTrue(thrown)
    }

    @Test
    fun `import key without enabling the key manager first works`() {
        oabe!!.keygen("|attr1|attr2", "key0")
        val keyBlob = oabe!!.exportUserKey("key0")
        oabe!!.importUserKey("key0", keyBlob)
    }

    @Test
    fun `export user key without importing it first fails`() {
        oabe!!.enableKeyManager("user1")
        var thrown = false
        try {
            oabe!!.exportUserKey("key0")
        } catch (e: OpenABECryptoContextExportKey) {
            thrown = true
        }
        assertTrue(thrown)
    }

    @Test
    fun `import user key and export user key works`() {
        oabe!!.keygen("|attr1|attr2", "key0")
        val keyBlob0 = oabe!!.exportUserKey("key0")
        oabe!!.importUserKey("key0", keyBlob0)
        val keyBlob1 = oabe!!.exportUserKey("key0")
        assertEquals(keyBlob0, keyBlob1)
    }

    @Test
    fun `import user key and delete user key works`() {
        oabe!!.keygen("|attr1|attr2", "key0")
        val keyBlob0 = oabe!!.exportUserKey("key0")
        oabe!!.importUserKey("key0", keyBlob0)
        assertTrue(oabe!!.deleteKey("key0"))
    }

    @Test
    fun `enable key manager and delete user key without importing it first works`() {
        oabe!!.deleteKey("key0")
    }

    @Test
    fun `two oabe contexts have different parameters`() {
        val mpk = oabe!!.exportPublicParams()
        val msk = oabe!!.exportSecretParams()
        val newOABE = OpenABECryptoContext(SchemeID.CP_ABE)
        newOABE.generateParams()
        val mpk2 = newOABE.exportPublicParams()
        val msk2 = newOABE.exportSecretParams()
        assertNotEquals(mpk, mpk2)
        assertNotEquals(msk, msk2)
        newOABE.destroy()
    }

    @Test
    fun `import and export params works`() {
        val mpk = oabe!!.exportPublicParams()
        val msk = oabe!!.exportSecretParams()
        val newOABE = OpenABECryptoContext(SchemeID.CP_ABE)
        newOABE.importPublicParams(mpk)
        newOABE.importSecretParams(msk)
        val mpk2 = newOABE.exportPublicParams()
        val msk2 = newOABE.exportSecretParams()
        newOABE.destroy()
        assertEquals(mpk, mpk2)
        assertEquals(msk, msk2)
    }
}
