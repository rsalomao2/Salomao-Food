package com.salomao.domain.di

import com.salomao.data.repository.PlaceRepository
import com.salomao.data.repository.PlaceRepositoryImpl
import com.salomao.data.service.PlaceService
import com.salomao.domain.builder.createRetrofit
import com.salomao.domain.usecase.GPSUseCase
import com.salomao.domain.usecase.GPSUseCaseImpl
import com.salomao.presentation.placelist.view.PlaceListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules

fun injectFirstKoin() = loadKoinModule

private val loadKoinModule by lazy {
    loadKoinModules(
        serviceModule,
        repositoryModule,
        viewModelModule,
        useCaseModule
    )
}

private val viewModelModule: Module = module {
    viewModel { PlaceListViewModel(repository = get(), gpsUseCase = get()) }
}

private val repositoryModule = module {
    single<PlaceRepository> { PlaceRepositoryImpl(contextProvider = get(), service = get()) }
}

private val serviceModule = module {
    single { createRetrofit<PlaceService>(okHttpClient = get()) }
}

private val useCaseModule = module {
    single<GPSUseCase> { GPSUseCaseImpl(contextProvider = get(), context = androidContext()) }
}
