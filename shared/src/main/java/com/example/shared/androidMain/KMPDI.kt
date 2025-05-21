package com.example.shared.androidMain

import com.example.shared.commonMain.repository.CharacterRepository
import com.example.shared.commonMain.repository.CharacterRepositoryImpl
import com.example.shared.commonMain.usecase.CharacterUseCase
import com.example.shared.commonMain.usecase.CharacterUseCaseImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module


object KMPDI {
    val shared = module {
        factory {
            HttpClient(OkHttp) {
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                    })
                }
            }
        }
        factory<CharacterRepository> { CharacterRepositoryImpl(get()) }
        factory<CharacterUseCase> { CharacterUseCaseImpl(get()) }
    }
}