package it.stefanoberlato

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LibopenabeInitializerTest {

    @Test
    fun `test library initialization and shutdown`() {

        /** library is not loaded nor initialized by default */
        assertFalse(LibopenabeInitializer.isLibraryLoadedYet())
        assertFalse(LibopenabeInitializer.isPlatformInitializedYet())

        /** initialize library once works */
        testBlocking {
            LibopenabeInitializer.initialize()
            assertTrue(LibopenabeInitializer.isLibraryLoadedYet())
            assertTrue(LibopenabeInitializer.isPlatformInitializedYet())
            LibopenabeInitializer.shutdown()
            assertTrue(LibopenabeInitializer.isLibraryLoadedYet())
            assertFalse(LibopenabeInitializer.isPlatformInitializedYet())
        }

        /** initialize library twice does not raise any problem */
        testBlocking {
            LibopenabeInitializer.initialize()
            assertTrue(LibopenabeInitializer.isLibraryLoadedYet())
            assertTrue(LibopenabeInitializer.isPlatformInitializedYet())
            LibopenabeInitializer.shutdown()
            assertTrue(LibopenabeInitializer.isLibraryLoadedYet())
            assertFalse(LibopenabeInitializer.isPlatformInitializedYet())

        }

        /** initialize library with callback works and executes callback */
        testBlocking {
            var callbackWasInvoked = false
            LibopenabeInitializer.initializeWithCallback {
                callbackWasInvoked = true
                assertTrue(LibopenabeInitializer.isLibraryLoadedYet())
                assertTrue(LibopenabeInitializer.isPlatformInitializedYet())
                testBlocking {
                    LibopenabeInitializer.shutdown()
                }
                assertTrue(LibopenabeInitializer.isLibraryLoadedYet())
                assertFalse(LibopenabeInitializer.isPlatformInitializedYet())
            }
            assertTrue(callbackWasInvoked)
        }
    }
}
