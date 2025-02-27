package com.desafio.android.data.repository

import com.desafio.android.core.factory.toFlow
import com.desafio.android.core.repository.CoinApiService
import com.desafio.android.data.local.ExchangeDao
import com.desafio.android.data.mapper.ExchangeMapperDto
import com.desafio.android.data.mapper.ExchangeResponseMapper
import com.desafio.android.data.model.Exchange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface ExchangeRepository {
    suspend fun getDataFromApi(): Flow<List<Exchange>>
    suspend fun saveExchanges(it: List<Exchange>)
    suspend fun getExchangesByCacheAndId(id: String): Flow<Exchange>
}

class ExchangeRepositoryImpl(
    private val api: CoinApiService,
    private val dao: ExchangeDao
) : ExchangeRepository {

    override suspend fun getDataFromApi(): Flow<List<Exchange>> =
        api.getExchanges().toFlow().map {
            ExchangeResponseMapper.transformToList(it)
        }

    override suspend fun saveExchanges(it: List<Exchange>) {
        dao.deleteAll()
        dao.insertAll(ExchangeMapperDto.transformToList(it))
    }

    override suspend fun getExchangesByCacheAndId(id: String): Flow<Exchange> {
        return dao.getExchangeById(id)
            .map { exchangeDto ->
                ExchangeMapperDto.transformToReverse(exchangeDto)
            }
    }


}