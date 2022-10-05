package it.stefanoberlato.oabe

import it.stefanoberlato.LibopenabeInitializer
import it.stefanoberlato.testBlocking
import kotlin.random.Random
import kotlin.test.*

class OpenPKSIGContextTest {

    private val myMessage = "Hello! I'm using Kotlin Multiplatform OpenABE."

    private var osig: OpenPKSIGContext? = null

    @BeforeTest
    fun initializeLibraryAndContext() {
        testBlocking {
            LibopenabeInitializer.initialize()
        }
        osig = OpenPKSIGContext(ECID.NIST_P256)
    }

    @AfterTest
    fun tearDown() {
        testBlocking {
            LibopenabeInitializer.shutdown()
        }
    }

    @Test
    fun `invoke any function after destroy fails`() {
        val newOsig = OpenPKSIGContext(ECID.NIST_P256)
        newOsig.destroy()
        var thrown = false
        try {
            newOsig.keygen(keyID = "key0")
        } catch (e: OpenPKSIGContextDestroyed) {
            thrown = true
        }
        assertTrue(thrown)
    }


    @Test
    fun `sign and verify with the same key works`() {
        osig!!.keygen(keyID = "public_A")
        val message = myMessage
        val signature = osig!!.sign(
            keyID = "public_A",
            message = message
        )
        osig!!.verify(
            keyID = "public_A",
            message = message,
            signature = signature
        )
    }

    @Test
    fun `verify with different key fails`() {
        osig!!.keygen(keyID = "public_A")
        osig!!.keygen(keyID = "public_B")
        val message = myMessage
        val signature = osig!!.sign(
            keyID = "public_A",
            message = message
        )
        var thrown = false
        try {
            osig!!.verify(
                keyID = "public_B",
                message = message,
                signature = signature
            )
        } catch (e: OpenPKSIGContextVerify) {
            thrown = true
        }
        assertTrue(thrown)
    }

    @Test
    fun `sign and verify with non existing key fails`() {
        osig!!.keygen(keyID = "public_A")
        val message = myMessage
        val signature = osig!!.sign(
            keyID = "public_A",
            message = message
        )

        var thrown = false
        try {
            osig!!.sign(
                keyID = "public_B",
                message = message
            )
        } catch (e: OpenPKSIGContextSign) {
            thrown = true
        }
        assertTrue(thrown)

        thrown = false
        try {
            osig!!.verify(
                keyID = "public_B",
                message = message,
                signature = signature
            )
        } catch (e: OpenPKSIGContextVerify) {
            thrown = true
        }
        assertTrue(thrown)
    }

    @Test
    fun `verify tampered message with right signature fails`() {
        osig!!.keygen(keyID = "public_A")
        val message = myMessage
        val signature = osig!!.sign(
            keyID = "public_A",
            message = message
        )

        val messageByteArray = message.encodeToByteArray()
        var index1: Int
        var index2: Int
        do {
            index1 = Random.nextInt(0, messageByteArray.size)
            index2 = Random.nextInt(0, messageByteArray.size)
        } while (messageByteArray[index1] == messageByteArray[index2])
        messageByteArray[index1] = messageByteArray[index2].also {
            messageByteArray[index2] = messageByteArray[index1]
        }

        var thrown = false
        try {
            osig!!.verify(
                keyID = "public_A",
                message = messageByteArray.decodeToString(),
                signature = signature
            )
        } catch (e: OpenPKSIGContextVerify) {
            thrown = true
        }
        assertTrue(thrown)
    }

    @Test
    fun `verify message with tampered signature fails`() {
        osig!!.keygen(keyID = "public_A")
        val message = myMessage
        val signature = osig!!.sign(
            keyID = "public_A",
            message = message
        )

        val signatureByteArray = signature.encodeToByteArray()
        var index1: Int
        var index2: Int
        do {
            index1 = Random.nextInt(0, signatureByteArray.size)
            index2 = Random.nextInt(0, signatureByteArray.size)
        } while (signatureByteArray[index1] == signatureByteArray[index2])
        signatureByteArray[index1] = signatureByteArray[index2].also {
            signatureByteArray[index2] = signatureByteArray[index1]
        }

        var thrown = false
        try {
            osig!!.verify(
                keyID = "public_A",
                message = message,
                signature = signatureByteArray.decodeToString()
            )
        } catch (e: OpenPKSIGContextVerify) {
            thrown = true
        }
        assertTrue(thrown)
    }

    @Test
    fun `export public and private user key without importing it first fails`() {
        var thrown = false
        try {
            osig!!.exportPublicKey(keyID = "public_A")
        } catch (e: OpenPKSIGContextExportKey) {
            thrown = true
        }
        assertTrue(thrown)

        thrown = false
        try {
            osig!!.exportPrivateKey(keyID = "public_A")
        } catch (e: OpenPKSIGContextExportKey) {
            thrown = true
        }
        assertTrue(thrown)
    }

    @Test
    fun `import user key and export user key works`() {
        osig!!.keygen(keyID = "public_A")
        val keyBlobPA = osig!!.exportPublicKey(keyID = "public_A")
        val keyBlobSA = osig!!.exportPrivateKey(keyID = "public_A")
        osig!!.importPublicKey(keyID = "public_A", keyBlob = keyBlobPA)
        osig!!.importPrivateKey(keyID = "public_A", keyBlob = keyBlobSA)
        assertEquals(keyBlobPA, osig!!.exportPublicKey(keyID = "public_A"))
        assertEquals(keyBlobSA, osig!!.exportPrivateKey(keyID = "public_A"))
    }




    @Test
    fun `my test to delete`() {
        osig!!.keygen(keyID = "public_A")
    }
}
