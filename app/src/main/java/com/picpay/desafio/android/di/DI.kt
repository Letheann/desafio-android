package com.picpay.desafio.android.di

import com.google.gson.GsonBuilder
import com.picpay.desafio.android.core.di.QualifierPicPayApi
import com.picpay.desafio.android.core.di.QualifierRetrofit
import com.picpay.desafio.android.core.factory.NetworkResponseAdapterFactory
import com.picpay.desafio.android.core.repository.PicPayService
import com.picpay.desafio.android.data.repository.PicPayRepository
import com.picpay.desafio.android.data.repository.PicPayRepositoryImpl
import com.picpay.desafio.android.domain.PicPayUseCase
import com.picpay.desafio.android.domain.PicPayUseCaseImpl
import com.picpay.desafio.android.presentation.MainActivityViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DI {

    private val repository = module {
        single(QualifierRetrofit) { provideRetrofit() }
        factory(QualifierPicPayApi) { provideApiRetrofitHost(get(QualifierRetrofit)) }
        factory<PicPayRepository> {
            PicPayRepositoryImpl(get(QualifierPicPayApi))
        }
    }

    private val useCase = module {
        factory<PicPayUseCase> { PicPayUseCaseImpl(picPayRepository = get()) }
    }

    private val presentation = module {
        viewModel { MainActivityViewModel(useCase = get()) }
    }

    val modules = listOf(repository, useCase, presentation)

    private fun provideApiRetrofitHost(retrofit: Retrofit): PicPayService = retrofit.create(
        PicPayService::class.java
    )

    private fun provideRetrofit(
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/")
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .addCallAdapterFactory(NetworkResponseAdapterFactory())
        .build()
}