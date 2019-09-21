package com.salomao

import android.app.Application
import com.salomao.domain.di.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this)

    }
}
