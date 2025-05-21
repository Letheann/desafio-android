package com.desafio.android

import android.app.Application
import com.desafio.android.di.DI
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@AndroidApplication)
            modules(DI.modules)
        }
    }
}