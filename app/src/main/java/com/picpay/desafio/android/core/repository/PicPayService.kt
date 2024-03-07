package com.picpay.desafio.android.core.repository

import com.picpay.desafio.android.core.factory.NetworkResponse
import com.picpay.desafio.android.data.model.UserDto
import retrofit2.http.GET


interface PicPayService {
    @GET("users")
    suspend fun getUsers(): NetworkResponse<List<UserDto>>
}