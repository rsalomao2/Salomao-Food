package com.salomao.provider

import kotlin.coroutines.CoroutineContext

interface CoroutineContextProvider {
    val MAIN: CoroutineContext
    val IO: CoroutineContext
}
