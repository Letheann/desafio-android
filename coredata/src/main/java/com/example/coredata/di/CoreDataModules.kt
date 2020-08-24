package com.example.coredata.di

import com.example.coredata.repository.RetrofitBuilder
import com.example.coredata.repository.usecases.users.GetUsers
import com.example.coredata.repository.usecases.users.IGetUsers
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
        factory<IGetUsers> {
            GetUsers(
                get(),
                get()
            )
        }
    }
}
