package com.picpay.desafio.android

import android.app.Application
import android.content.Context
import com.example.coredata.di.CoreDataModules
import com.picpay.desafio.android.di.MainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    companion object {
        const val APPLICATION = "main"
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        val modules = MainModule.getModules()
        modules.addAll(CoreDataModules.getModules())
        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(modules)
        }
    }
}