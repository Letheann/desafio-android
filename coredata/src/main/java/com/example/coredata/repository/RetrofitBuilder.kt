package com.example.coredata.repository

import com.example.coredata.extensions.build
import com.example.coredata.extensions.provideInterface
import com.example.coredata.repository.usecases.service.PicPayService

import retrofit2.Retrofit


class RetrofitBuilder {
    private val retrofit: Retrofit = Retrofit.Builder().build("http://careers.picpay.com/tests/mobdev/")
    val service: PicPayService
        get() = retrofit.provideInterface()
}