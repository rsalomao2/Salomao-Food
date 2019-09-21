package com.salomao.provider

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class CoroutineContextProviderImpl : CoroutineContextProvider {
    override val MAIN: CoroutineContext by lazy { Dispatchers.Main }
    override val IO: CoroutineContext by lazy { Dispatchers.IO }
}
