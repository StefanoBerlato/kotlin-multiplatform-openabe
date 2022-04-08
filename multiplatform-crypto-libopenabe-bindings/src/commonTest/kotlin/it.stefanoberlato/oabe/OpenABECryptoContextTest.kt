package it.stefanoberlato.oabe

import it.stefanoberlato.LibopenabeInitializer
import it.stefanoberlato.SchemeID
import it.stefanoberlato.testBlocking
import kotlin.random.Random
import kotlin.test.*

class OpenABECryptoContextTest {

    private val myPlaintext = "The Legend of Vox Machina"

    private var oabe: OpenABECryptoContext? = null

    @BeforeTest
    fun initializeLibraryAndContext() {
        if (!LibopenabeInitializer.isInitialized()) {
            testBlocking {
                LibopenabeInitializer.initialize()
            }
        }
        oabe = OpenABECryptoContext(SchemeID.CP_ABE)
        oabe!!.enableVerbose()
        oabe!!.generateParams()
    }

    @AfterTest
    fun tearDown() {
        oabe!!.destroy()
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
        val newOabe = OpenABECryptoContext(SchemeID.CP_ABE)
        var thrown = false
        try {
            newOabe.keygen("|attr1|attr2", "key0")
        } catch (e: OpenABECryptoContextMissingParameters) {
            thrown = true
        }
        newOabe.destroy()
        assertTrue(thrown)
    }

    @Test
    fun `encrypt and decrypt with same key works`() {
        oabe!!.keygen("|attr1|attr2", "key0")
        val plaintext = myPlaintext
        val ciphertext = oabe!!.encrypt("attr1 and attr2", plaintext)
        val decrypted = oabe!!.decrypt("key0", ciphertext)
        assertEquals(plaintext, decrypted)
    }

    @Test
    fun `encrypt and decrypt with different key fails`() {
        oabe!!.keygen("|attr1", "key1")

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
    fun `encrypt and decrypt with not existing key fails`() {
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
    fun `encrypt and decrypt of tampered ciphertext with same key fails`() {
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
        // TODO throws a 'oabe::ZCryptoBoxException' but in the library, not in Kotlin
        //  => You need to handle the C exception yourself and instead build a java
        //  exception which can be passed to the java side of the code. Pass the
        //  exception from C back to Kotlin as a multiple parameter
        /*context!!.keygen("|attr1|attr2", "key0")

        val plaintext = myPlaintext
        val ciphertext = context!!.encrypt("attr1 and attr2", plaintext)

        val keyBlob = context!!.exportUserKey("key0")
        context!!.importUserKey("key0", keyBlob)

        val decrypted = context!!.decrypt(ciphertext)
        assertEquals(plaintext, decrypted)*/
    }

    @Test
    fun `enable key manager and not import key and decrypt with no key ID fails`() {
        // TODO throws a 'oabe::ZCryptoBoxException' but in the library, not in Kotlin
        //  => You need to handle the C exception yourself and instead build a java
        //  exception which can be passed to the java side of the code. Pass the
        //  exception from C back to Kotlin as a multiple parameter
        /*context!!.enableKeyManager("user1")
        context!!.keygen("|attr1|attr2", "key0")

        val plaintext = myPlaintext
        val ciphertext = context!!.encrypt("attr1 and attr2", plaintext)

        val decrypted = context!!.decrypt(ciphertext)
        assertEquals(plaintext, decrypted)*/
    }

    @Test
    fun `import key without enabling the key manager first works`() {
        oabe!!.keygen("|attr1|attr2", "key0")
        val keyBlob = oabe!!.exportUserKey("key0")
        oabe!!.importUserKey("key0", keyBlob)
    }

    @Test
    fun `export user key without importing it first fails`() {
        // TODO throws a 'oabe::ZCryptoBoxException' but in the library, not in Kotlin
        //  => You need to handle the C exception yourself and instead build a java
        //  exception which can be passed to the java side of the code. Pass the
        //  exception from C back to Kotlin as a multiple parameter
        /*context!!.enableKeyManager("user1")
        val keyBlob = context!!.exportUserKey("key0")*/
    }

    @Test
    fun `import user key and export user key works`() {
        oabe!!.keygen("|attr1|attr2", "key0")
        val keyBlob0 = oabe!!.exportUserKey("key0")
        oabe!!.importUserKey("key1", keyBlob0)
        val keyBlob1 = oabe!!.exportUserKey("key1")
        assertEquals(keyBlob0, keyBlob1)
    }

    @Test
    fun `import user key and delete user key works`() {
        oabe!!.keygen("|attr1|attr2", "key0")
        val keyBlob0 = oabe!!.exportUserKey("key0")
        oabe!!.importUserKey("key1", keyBlob0)
        assertTrue(oabe!!.deleteKey("key0"))
        assertTrue(oabe!!.deleteKey("key1"))
    }

    @Test
    fun `enable key manager and delete user key without importing it first works`() {
        oabe!!.deleteKey("key2")
    }

    @Test
    fun `two oabe contexts have different parameters`() {
        val mpk = oabe!!.exportPublicParams()
        val msk = oabe!!.exportSecretParams()
        val newOabe = OpenABECryptoContext(SchemeID.CP_ABE)
        newOabe.generateParams()
        val mpk2 = newOabe.exportPublicParams()
        val msk2 = newOabe.exportSecretParams()
        newOabe.destroy()
        assertNotEquals(mpk, mpk2)
        assertNotEquals(msk, msk2)
    }

    @Test
    fun `import and export params works`() {
        val mpk = oabe!!.exportPublicParams()
        val msk = oabe!!.exportSecretParams()
        val newOabe = OpenABECryptoContext(SchemeID.CP_ABE)
        newOabe.importPublicParams(mpk)
        newOabe.importSecretParams(msk)
        val mpk2 = newOabe.exportPublicParams()
        val msk2 = newOabe.exportSecretParams()
        newOabe.destroy()
        assertEquals(mpk, mpk2)
        assertEquals(msk, msk2)
    }
}


/**
 * TODO TEST, but no sure if multi-authority ABE is enabled or not
 * void openABECryptoContext_importPublicParamsWithAuthID(openABECryptoContext_t *m, const char * authID, const char * keyBlob);
 * void openABECryptoContext_importSecretParamsWithAuthID(openABECryptoContext_t *m, const char * authID, const char * keyBlob);
 */