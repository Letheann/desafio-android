package com.example.coredata.di

import com.example.coredata.repository.RetrofitBuilder
import com.example.coredata.repository.usecases.GetUsers
import com.example.coredata.utils.Utils
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


object CoreDataModules{
    fun getModules() = arrayListOf(
        apiModule
    )

    private val apiModule = module {
        single { RetrofitBuilder().service }
        factory { Utils(androidContext()) }
        factory { GetUsers(get(), get()) }
    }
}
