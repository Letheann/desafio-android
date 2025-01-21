package com.desafio.android.di

import android.app.Application
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.desafio.android.core.di.QualifierEncryptedSharedPreferences
import com.desafio.android.core.di.QualifierMasterKey
import com.desafio.android.core.di.RANDOM_KEY
import com.desafio.android.core.di.SHARED_PREF_NAME
import com.desafio.android.data.local.AppDatabase
import com.desafio.android.data.local.LogsDao
import com.desafio.android.data.repository.LoginRepository
import com.desafio.android.data.repository.LoginRepositoryImpl
import com.desafio.android.data.repository.LogsRepository
import com.desafio.android.data.repository.LogsRepositoryImpl
import com.desafio.android.domain.login.LoginUseCase
import com.desafio.android.domain.login.LoginUseCaseImpl
import com.desafio.android.domain.user.LogsUseCase
import com.desafio.android.domain.user.LogsUseCaseImpl
import com.desafio.android.presentation.login.presentation.LoginViewModel
import com.desafio.android.presentation.logs.presentation.LogsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


object DI {

    private val repository = module {
        factory<LogsRepository> {
            LogsRepositoryImpl(logsDao = get())
        }

        factory(com.desafio.android.core.di.QualifierMasterKey) {
            MasterKey.Builder(androidApplication(), MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
        }

        factory(com.desafio.android.core.di.QualifierEncryptedSharedPreferences) {
            EncryptedSharedPreferences.create(
                androidApplication(),
                com.desafio.android.core.di.SHARED_PREF_NAME,
                get(com.desafio.android.core.di.QualifierMasterKey),
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }

        factory<LoginRepository> {
            LoginRepositoryImpl(
                randomKey = com.desafio.android.core.di.RANDOM_KEY,
                gson = Gson(),
                encryptedSharedPreferences = get(com.desafio.android.core.di.QualifierEncryptedSharedPreferences),
                logsDao = get()
            )
        }
    }

    private val useCase = module {
        factory<LogsUseCase> { LogsUseCaseImpl(logsRepository = get()) }
        factory<LoginUseCase> { LoginUseCaseImpl(repository = get()) }
    }

    private val presentation = module {
        viewModel { LogsViewModel(useCase = get()) }
        viewModel { LoginViewModel(useCase = get()) }
    }

    private val databaseModule = module {

        fun provideDataBase(application: Application): AppDatabase {
            return Room.databaseBuilder(application, AppDatabase::class.java, "AppDatabase")
                .fallbackToDestructiveMigration()
                .build()
        }

        fun provideDao(dataBase: AppDatabase): LogsDao {
            return dataBase.logsDao()
        }
        single { provideDataBase(androidApplication()) }

        single { provideDao(get()) }
    }

    val modules = listOf(databaseModule, repository, useCase, presentation)

}