package com.salomao.domain.di

import com.salomao.presentation.placedetail.PlaceDetailViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules

fun injectPlaceDetailKoin() = loadKoinModule

private val loadKoinModule by lazy {
    loadKoinModules(
        viewModelModule
    )
}

private val viewModelModule: Module = module {
    viewModel {
        PlaceDetailViewModel(repository = get())
    }
}
