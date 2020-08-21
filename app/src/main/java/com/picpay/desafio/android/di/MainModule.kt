package com.picpay.desafio.android.di

import com.picpay.desafio.android.viewmodel.MainActivityViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object MainModule{

    fun getModules() = arrayListOf(mainModule)

    private val mainModule = module {
        viewModel { MainActivityViewModel(get()) }
    }

}
