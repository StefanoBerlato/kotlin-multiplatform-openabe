package it.stefanoberlato.oabe

import it.stefanoberlato.LibopenabeInitializer
import it.stefanoberlato.testBlocking
import kotlin.random.Random
import kotlin.test.*

class OpenPKEContextTest {

    private val myPlaintext = "Hello! I'm using Kotlin Multiplatform OpenABE."

    private var opke: OpenPKEContext? = null

    @BeforeTest
    fun initializeLibraryAndContext() {
        if (!LibopenabeInitializer.isInitialized()) {
            testBlocking {
                LibopenabeInitializer.initialize()
            }
        }
        opke = OpenPKEContext(ECID.NIST_P256)
    }

    @AfterTest
    fun tearDown() {
        opke!!.destroy()
    }

    @Test
    fun `invoke any function after destroy fails`() {
        val newOabe = OpenPKEContext(ECID.NIST_P256)
        newOabe.destroy()
        var thrown = false
        try {
            newOabe.keygen(keyID = "key0")
        } catch (e: OpenPKEContextDestroyed) {
            thrown = true
        }
        assertTrue(thrown)
    }


    @Test
    fun `encrypt and decrypt with the same key works`() {
        opke!!.keygen(keyID = "public_A")
        val plaintext = myPlaintext
        val ciphertext = opke!!.encrypt(
            receiverID = "public_A",
            plaintext = plaintext
        )
        val decrypted = opke!!.decrypt(
            receiverID = "public_A",
            ciphertext = ciphertext
        )
        assertEquals(plaintext, decrypted)
    }

    @Test
    fun `decrypt with different key fails`() {
        opke!!.keygen(keyID = "public_A")
        opke!!.keygen(keyID = "public_B")
        val plaintext = myPlaintext
        val ciphertext = opke!!.encrypt(
            receiverID = "public_A",
            plaintext = plaintext
        )
        var thrown = false
        try {
            opke!!.decrypt(
                receiverID = "public_B",
                ciphertext = ciphertext
            )
        } catch (e: OpenPKEContextDecrypt) {
            thrown = true
        }
        assertTrue(thrown)
    }

    @Test
    fun `encrypt and decrypt with non existing key fails`() {
        opke!!.keygen(keyID = "public_A")
        val plaintext = myPlaintext
        val ciphertext = opke!!.encrypt(
            receiverID = "public_A",
            plaintext = plaintext
        )

        var thrown = false
        try {
            opke!!.encrypt(
                receiverID = "public_B",
                plaintext = plaintext
            )
        } catch (e: OpenPKEContextEncrypt) {
            thrown = true
        }
        assertTrue(thrown)

        thrown = false
        try {
            opke!!.decrypt(
                receiverID = "public_B",
                ciphertext = ciphertext
            )
        } catch (e: OpenPKEContextDecrypt) {
            thrown = true
        }
        assertTrue(thrown)
    }

    @Test
    fun `decrypt tampered ciphertext with the same key fails`() {
        opke!!.keygen(keyID = "public_A")
        val plaintext = myPlaintext
        val ciphertext = opke!!.encrypt(
            receiverID = "public_A",
            plaintext = plaintext
        )

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
             opke!!.decrypt(
                receiverID = "public_A",
                ciphertext = ciphertextByteArray.decodeToString()
            )
        } catch (e: OpenPKEContextDecrypt) {
            thrown = true
        }
        assertTrue(thrown)
    }

    @Test
    fun `export public and private user key without importing it first fails`() {
        var thrown = false
        try {
            opke!!.exportPublicKey(keyID = "public_A")
        } catch (e: OpenPKEContextExportKey) {
            thrown = true
        }
        assertTrue(thrown)

        thrown = false
        try {
            opke!!.exportPrivateKey(keyID = "public_A")
        } catch (e: OpenPKEContextExportKey) {
            thrown = true
        }
        assertTrue(thrown)
    }

    @Test
    fun `import user key and export user key works`() {
        opke!!.keygen(keyID = "public_A")
        val keyBlobPA = opke!!.exportPublicKey(keyID = "public_A")
        val keyBlobSA = opke!!.exportPrivateKey(keyID = "public_A")
        opke!!.importPublicKey(keyID = "public_B", keyBlob = keyBlobPA)
        opke!!.importPrivateKey(keyID = "public_B", keyBlob = keyBlobSA)
        assertEquals(keyBlobPA, opke!!.exportPublicKey(keyID = "public_B"))
        assertEquals(keyBlobSA, opke!!.exportPrivateKey(keyID = "public_B"))
    }
}
