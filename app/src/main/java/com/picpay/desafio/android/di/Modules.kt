package com.picpay.desafio.android.di

import com.picpay.desafio.android.repository.usecases.GetUsers
import com.picpay.desafio.android.viewmodel.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single { GetUsers() }
    viewModel { MainActivityViewModel(get()) }
}