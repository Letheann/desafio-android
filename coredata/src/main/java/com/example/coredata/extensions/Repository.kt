package com.example.coredata.extensions

import android.content.Context
import com.example.coredata.utils.Utils
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun Retrofit.Builder.build(url: String): Retrofit {
    return this.baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

inline fun <reified R : Any> Retrofit.provideInterface(): R {
    return this.create(R::class.java)
}

inline fun <reified R : Any> Call<R>.safeRequestCheckingNetwork(utils: Utils): R? {
    return if (utils.checkNetworkState()) {
        this.execute().body()
    } else {
        this.cancel()
        null
    }
}
