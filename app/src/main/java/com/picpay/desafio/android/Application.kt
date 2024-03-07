package com.picpay.desafio.android

import androidx.multidex.MultiDexApplication
import com.picpay.desafio.android.di.DI
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@Application)
            modules(DI.modules)
        }
    }
}