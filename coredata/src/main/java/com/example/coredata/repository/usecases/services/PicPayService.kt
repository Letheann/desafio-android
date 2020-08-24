package com.example.coredata.repository.usecases.services

import com.example.coredata.models.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET


interface PicPayService {
    @GET("users")
    fun getUsers(): Call<List<User>>
}