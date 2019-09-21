package com.salomao.domain.provider

import kotlin.coroutines.CoroutineContext

interface CoroutineContextProvider {
    val MAIN: CoroutineContext
    val IO: CoroutineContext
}
