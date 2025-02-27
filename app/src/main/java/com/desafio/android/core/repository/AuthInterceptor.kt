package com.desafio.android.core.repository

import com.desafio.android.core.di.KEY
import okhttp3.Interceptor

class AuthInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request().newBuilder()
            .addHeader(KEY, apiKey)
            .build()
        return chain.proceed(request)
    }
}