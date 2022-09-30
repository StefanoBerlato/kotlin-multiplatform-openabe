package it.stefanoberlato.oabe.crypto

import it.stefanoberlato.LibopenabeInitializer
import it.stefanoberlato.testBlocking
import kotlin.random.Random
import kotlin.test.*

class OpenABESymKeyHandleImplTest {

    private val myPlaintext = "Hello! I'm using Kotlin Multiplatform OpenABE."

    private var osym: OpenABESymKeyHandleImpl? = null
    private var key: ByteArray? = null

    @BeforeTest
    fun initializeLibraryAndContext() {
        testBlocking {
            LibopenabeInitializer.initialize()
        }
        key = Zsymcrypto.generateSymmetricKey(32)
        osym = OpenABESymKeyHandleImpl(key!!)
    }

    @AfterTest
    fun tearDown() {
        osym!!.destroy()
        testBlocking {
            LibopenabeInitializer.shutdown()
        }
    }

    @Test
    fun `invoke any function after destroy fails`() {
        val newKey = Zsymcrypto.generateSymmetricKey(32)
        val newOsym = OpenABESymKeyHandleImpl(newKey)
        newOsym.destroy()
        var thrown = false
        try {
            newOsym.exportKey()
        } catch (e: OpenABESymKeyHandleImplDestroyed) {
            thrown = true
        }
        assertTrue(thrown)
    }

    @Test
    fun `encrypt and decrypt with the same key works`() {
        val plaintext = myPlaintext
        val ciphertext = osym!!.encrypt(plaintext)
        val decrypted = osym!!.decrypt(ciphertext)
        assertEquals(plaintext, decrypted)
    }
    @Test
    fun `decrypt with a different key fails`() {
        val plaintext = myPlaintext
        val ciphertext = osym!!.encrypt(plaintext)

        val key2 = Zsymcrypto.generateSymmetricKey(32)
        val osym2 = OpenABESymKeyHandleImpl(key2)

        var thrown = false
        try {
            osym2.decrypt(ciphertext)
        } catch (e: OpenABESymKeyHandleImplRunTime) {
            thrown = true
        }
        assertTrue(thrown)
    }

    @Test
    fun `decrypt tampered ciphertext fails`() {
        val plaintext = myPlaintext
        val ciphertext = osym!!.encrypt(plaintext)
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
        val decrypted = try {
            osym!!.decrypt(ciphertextByteArray.decodeToString())
        } catch (e: OpenABESymKeyHandleImplRunTime) {
            thrown = true
            null
        }
        assertTrue((thrown && decrypted == null) || (decrypted!! != plaintext))
    }

    @Test
    fun `export key works`() {
        osym!!.exportKey()
    }
    @Test
    fun `export raw key works`() {
        val exportedRawKey = osym!!.exportRawKey()
        assertEquals(key!!.decodeToString(), exportedRawKey)
    }
}
