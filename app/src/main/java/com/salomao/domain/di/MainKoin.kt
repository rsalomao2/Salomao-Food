package com.salomao.domain.di

import android.app.Application
import com.salomao.domain.builder.createOkHttpClient
import com.salomao.domain.provider.*
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.with
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext

lateinit var okHttpClient: OkHttpClient

fun startKoin(myApplication: Application) {
    StandAloneContext.startKoin(
        listOf(
            networkModule,
            contextModule,
            providerModule
        )
    ) with myApplication

    okHttpClient = createOkHttpClient()
}

private val networkModule = module {
    single { okHttpClient }
}

private val contextModule = module {
    single<CoroutineContextProvider> { CoroutineContextProviderImpl() }
}

private val providerModule = module {
    single<StringProvider> { StringProviderImpl(androidContext()) }
    single<CoroutineContextProvider> { CoroutineContextProviderImpl() }
}
