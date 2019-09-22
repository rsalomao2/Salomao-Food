package com.salomao.domain.provider

import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class TestContextProvider : CoroutineContextProvider {
    override val MAIN: CoroutineContext = Unconfined
    override val IO: CoroutineContext = Unconfined
}
