package it.stefanoberlato

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.Continuation
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine

fun testBlocking(block : suspend () -> Unit) {
    val continuation = Continuation<Unit>(EmptyCoroutineContext) {
        if (it.isFailure) {
            throw it.exceptionOrNull()!!
        }
    }
    block.startCoroutine(continuation)
}

expect fun runTest(block: suspend (scope : CoroutineScope) -> Unit)

