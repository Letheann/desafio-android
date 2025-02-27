package com.desafio.android.domain

import com.desafio.android.data.model.Exchange
import com.desafio.android.data.repository.ExchangeRepository
import kotlinx.coroutines.flow.Flow

interface ExchangeUseCase {
    suspend fun getExchanges(): Flow<List<Exchange>>
    suspend fun saveExchanges(it: List<Exchange>)
    suspend fun getExchangesByCacheAndId(id: String): Flow<Exchange>
}

class ExchangeUseCaseImpl(private val repository: ExchangeRepository) : ExchangeUseCase {
    override suspend fun getExchanges(): Flow<List<Exchange>> = repository.getDataFromApi()
    override suspend fun saveExchanges(it: List<Exchange>) {
        repository.saveExchanges(it)
    }

    override suspend fun getExchangesByCacheAndId(id: String): Flow<Exchange> =
        repository.getExchangesByCacheAndId(id)

}