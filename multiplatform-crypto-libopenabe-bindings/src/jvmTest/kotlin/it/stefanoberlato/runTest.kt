package it.stefanoberlato

import it.stefanoberlato.oabe.OpenABECryptoContext
import it.stefanoberlato.oabe.SchemeID
import kotlinx.coroutines.*
import org.junit.Test
import java.util.*
import kotlin.random.Random
import kotlin.test.assertEquals

actual fun runTest(block: suspend (scope : CoroutineScope) -> Unit) = runBlocking { block(this) }

class OpenABECryptoContextTest {

    private var oabe: OpenABECryptoContext? = null

    @Test
    fun sigsegvReplication() {
        LibopenabeInitializer.openabeJna = LibopenabeInitializer.loadLibrary()
        println("runBlocking:" + Thread.currentThread())

        /** Uncomment one of the invocations below */


        /** This will cause a SIGSEGV error */
//        sigsegvDispatchersDefault()

        /** This will not cause a SIGSEGV error */
//        noSigsegvDispatchersUnconfined()

        /** This will not cause a SIGSEGV error */
//        noSigsegvInitializeInsideJob()
    }

    private fun sigsegvDispatchersDefault() {
        runBlocking {
            LibopenabeInitializer.openabeJna.InitializeOpenABE()
            launch(context = Dispatchers.Default) { /** Dispatchers.Default => SIGSEGV */
                println("job:" + Thread.currentThread())
                encryptDecryptWorks()
            }.join()
        }
    }

    private fun noSigsegvDispatchersUnconfined() {
        runBlocking {
            LibopenabeInitializer.openabeJna.InitializeOpenABE()
            launch(context = Dispatchers.Unconfined) {/** Dispatchers.Unconfined => NO SIGSEGV */
                println("job:" + Thread.currentThread())
                encryptDecryptWorks()
            }.join()
        }
    }

    private fun noSigsegvInitializeInsideJob() {
        runBlocking {
            launch(context = Dispatchers.Default) {
                LibopenabeInitializer.openabeJna.InitializeOpenABE() /** Init within job => NO SIGSEGV */
                println("job:" + Thread.currentThread())
                encryptDecryptWorks()
            }.join()
        }
    }

    private suspend fun encryptDecryptWorks() {
        oabe = OpenABECryptoContext(SchemeID.CP_ABE)
        oabe!!.enableVerbose()
        oabe!!.generateParams()
        oabe!!.keygen(
            keyID = "key0",
            keyInput = "|attr1|attr2",
        )
        val plaintext = "A sample plaintext to encrypt"
        val ciphertext = oabe!!.encrypt(
            encInput = "attr1 and attr2",
            plaintext = plaintext,
        )
        val decrypted = oabe!!.decrypt(
            keyID = "key0",
            ciphertext = ciphertext,
        )
        assertEquals(plaintext, decrypted)
        LibopenabeInitializer.shutdown()
    }
}

