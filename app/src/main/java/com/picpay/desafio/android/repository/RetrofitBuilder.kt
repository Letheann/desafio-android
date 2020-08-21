package com.picpay.desafio.android.repository

import com.picpay.desafio.android.BuildConfig
import com.picpay.desafio.android.helper.extensions.build
import com.picpay.desafio.android.helper.extensions.provideInterface
import retrofit2.Retrofit


class RetrofitBuilder {
    private val retrofit: Retrofit = Retrofit.Builder().build("http://careers.picpay.com/tests/mobdev/")
    val service: PicPayService
        get() = retrofit.provideInterface()
}