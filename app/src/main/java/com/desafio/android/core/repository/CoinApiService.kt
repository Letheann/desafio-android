package com.desafio.android.core.repository

import com.desafio.android.core.factory.NetworkResponse
import com.desafio.android.data.model.ExchangeResponse
import retrofit2.http.GET


interface CoinApiService {
    @GET("/v1/exchanges")
    suspend fun getExchanges(): NetworkResponse<List<ExchangeResponse>>
}