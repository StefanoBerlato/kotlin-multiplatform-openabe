package it.stefanoberlato

import it.stefanoberlato.oabe.LibopenabeUtil.shutdownOpenABE
import it.stefanoberlato.oabe.OpenABECryptoContext
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.Continuation
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine
import kotlin.test.Test
import kotlin.test.assertEquals

fun testBlocking(block : suspend () -> Unit) {
    val continuation = Continuation<Unit>(EmptyCoroutineContext) {
        //Do nothing
        if (it.isFailure) {
            throw it.exceptionOrNull()!!
        }
    }
    block.startCoroutine(continuation)
}

expect fun runTest(block: suspend (scope : CoroutineScope) -> Unit)

