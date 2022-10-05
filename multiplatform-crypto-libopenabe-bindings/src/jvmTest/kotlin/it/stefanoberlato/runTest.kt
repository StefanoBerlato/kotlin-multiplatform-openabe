package it.stefanoberlato

import it.stefanoberlato.oabe.OpenABECryptoContext
import it.stefanoberlato.oabe.SchemeID
import kotlinx.coroutines.*
import org.junit.Test
import java.util.*
import kotlin.random.Random
import kotlin.test.assertEquals

actual fun runTest(block: suspend (scope : CoroutineScope) -> Unit) = runBlocking { block(this) }
