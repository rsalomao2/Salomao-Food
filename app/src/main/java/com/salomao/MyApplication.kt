package com.salomao

import android.app.Application
import com.salomao.di.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this)

    }
}
