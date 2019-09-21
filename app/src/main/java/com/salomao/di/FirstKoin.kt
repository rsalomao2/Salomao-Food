package com.salomao.di

import com.salomao.first.view.FirstViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules

fun injectFirstKoin() = loadKoinModule

private val loadKoinModule by lazy {
    loadKoinModules(
        viewModelModule
    )
}

private val viewModelModule: Module = module {
    viewModel { FirstViewModel() }
}
