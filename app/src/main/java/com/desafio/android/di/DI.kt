package com.desafio.android.di

import com.desafio.android.presentation.presentation.WelcomeViewModel
import com.example.shared.androidMain.KMPDI
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


object DI {
    private val presentation = module {
        viewModel { WelcomeViewModel(useCase = get()) }
    }

    val modules = listOf(presentation, KMPDI.shared)
}