package it.stefanoberlato

import it.stefanoberlato.oabe.LibopenabeUtil
import it.stefanoberlato.oabe.OpenABECryptoContext
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LibopenabeInitializerTest {

    @Test // TODO delete this
    fun deletemne() {
        // TODO stuff used for testing Openabe bindings
        testBlocking {
            LibopenabeInitializer.initialize()
        }

        println("Hello, Testing bindings for OpenABE from bindings project!")

        println("Creating Context")
        val context = OpenABECryptoContext(SchemeID.CP_ABE)

        println("Importing params")
        context.importPublicParams("AAAAFqpvyTigccWQUPm+Do3U1ZTVarNtcGsAAAHToQFBsgEEtLIBAAToPLd/zTx2SUNGiA3AKENE/LDzAGE6DdqS/jYqnfahAqGFVIVGFe3LCOnnm+1YkDtrvX8Tq/CJqECbYFJrX00N3sflsH0QYDoz+B2qkvxPkXg+M8/F9evc6okFm2NAZBvZj7W7xOkMhdmmhHCdcNNyZwiO2oqQf0g1VSkbRsJXHKtq3od6ExvlkrbTF0PwT3/aQKPqTKE2K33fuxSz35YFrMCmZDyT/PVzbRUl5hmuw9zktzk4Ns/QlwhjsLOB4Ri0jt7qCjbekiZq2wYwvgKd6KgppG6mv+dxiEOrAknCH6y0GOD9kpHCMP8Jk8THw9Kj5XBHD420gvjYRxjO+ROhAmcxoSSyoSECDDxJCHj3fY4vYC2w6WQQt8K/PzchTIQWP+Cw4aryZfyhA2cxYaEksqEhAgv7dND9QQ1DAlzc2MsMX+NxU7NC5WasI/2wfWih+yC2oQJnMqFEs6FBAgik8YNXJ7Seb9uHXCTHwZSvhykAXq6oV2ey10zGfg+AHeaBt1orZ09OMXCIM0xSykIlBGUCAr4H3Xc0N2GvzZqhAWuhJR0AAAAgjvF0DWl84MGsM7/UcSNpXsuBxnnqG3CmMbrJ/O28Sy4=")
        context.importSecretParams("AAAAFqpvyQof8fx//ZSeeUZwROq+vYttc2sAAAB3oQVhbHBoYaEjsQAgE7fBHqR90fSHFrIKdsE1m0cb2P4W7Kd6ubP5XKVTlUOhA2cyYaFEs6FBAxbh420h3SZbbOB+Q9nDpKvA24LDvjjz3376dJ678DoBGingLELbvQ/0B+Tne/qspMNCWKuiy41P/vG/HLkk1Dw=")
        context.enableVerbose()

        println("Generating key")
        context.keygen("|attr1|attr2|", "key0")

        val plaintext = "nativehubbabubba"
        println("Encrypting plaintext: $plaintext")
        val ciphertext1 = context.encrypt("attr1 and attr2", plaintext)
        println("Resulting ciphertext1 is: $ciphertext1")

        println("Decrypting ciphertext: $ciphertext1")
        val decrypted = context.decrypt("key0", ciphertext1)
        println("Resulting plaintext is: $decrypted")

        assertEquals(plaintext, decrypted)

        println("Destroying context")
        context.destroy()

        println("Shutdown OpenABE")
        LibopenabeUtil.shutdownOpenABE()
    }

    @Test
    fun `test library initialization`() {

        /** library is not initialized by default */
        assertFalse(LibopenabeInitializer.isInitialized())

        /** initialize library once works */
        testBlocking {
            LibopenabeInitializer.initialize()
            assertTrue(LibopenabeInitializer.isInitialized())
        }

        /** initialize library twice does not raise any problem */
        testBlocking {
            LibopenabeInitializer.initialize()
            assertTrue(LibopenabeInitializer.isInitialized())
        }

        /** initialize library with callback works and executes callback */
        testBlocking {
            var callbackWasInvoked = false
            LibopenabeInitializer.initializeWithCallback {
                callbackWasInvoked = true
            }
            assertTrue(callbackWasInvoked)
            assertTrue(LibopenabeInitializer.isInitialized())
        }
    }
}
