package it.stefanoberlato

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LibopenabeInitializerTest {

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
