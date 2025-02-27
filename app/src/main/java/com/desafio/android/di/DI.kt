package com.desafio.android.di

import android.app.Application
import androidx.room.Room
import br.com.desafio.androidapp.BuildConfig
import com.desafio.android.core.di.QualifierApi
import com.desafio.android.core.di.QualifierRetrofit
import com.desafio.android.core.factory.NetworkResponseAdapterFactory
import com.desafio.android.core.repository.AuthInterceptor
import com.desafio.android.core.repository.CoinApiService
import com.desafio.android.data.local.AppDatabase
import com.desafio.android.data.local.ExchangeDao
import com.desafio.android.data.repository.ExchangeRepository
import com.desafio.android.data.repository.ExchangeRepositoryImpl
import com.desafio.android.domain.ExchangeUseCase
import com.desafio.android.domain.ExchangeUseCaseImpl
import com.desafio.android.presentation.presentation.WelcomeViewModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object DI {

    private val repository = module {
        single(QualifierRetrofit) { provideRetrofit() }
        factory(QualifierApi) { provideApiRetrofitHost(get(QualifierRetrofit)) }

        factory<ExchangeRepository> {
            ExchangeRepositoryImpl(
                api = get(QualifierApi),
                dao = get()
            )
        }
    }

    private val useCase = module {
        factory<ExchangeUseCase> { ExchangeUseCaseImpl(repository = get()) }
    }

    private val presentation = module {
        viewModel { WelcomeViewModel(useCase = get()) }
    }

    private val databaseModule = module {

        fun provideDataBase(application: Application): AppDatabase {
            return Room.databaseBuilder(application, AppDatabase::class.java, "AppDatabase")
                .fallbackToDestructiveMigration()
                .build()
        }

        fun provideDao(dataBase: AppDatabase): ExchangeDao {
            return dataBase.ExchangeDao()
        }
        single { provideDataBase(androidApplication()) }

        single { provideDao(get()) }
    }

    val modules = listOf(databaseModule, repository, useCase, presentation)

    private fun provideApiRetrofitHost(retrofit: Retrofit): CoinApiService = retrofit.create(
        CoinApiService::class.java
    )

    private fun provideRetrofit(
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(provideClient())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .addCallAdapterFactory(NetworkResponseAdapterFactory())
        .build()

    private fun provideClient() = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(BuildConfig.KEY_PUBLIC))
        .build()

}