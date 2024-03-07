package com.picpay.desafio.android.core.factory

import com.picpay.desafio.android.core.factory.NetworkResponseCall
import java.lang.reflect.Type
import retrofit2.Call
import retrofit2.CallAdapter

class NetworkResponseAdapter(
    private val responseType: Type
): CallAdapter<Type, Any> {

    override fun responseType(): Type = responseType
    override fun adapt(call: Call<Type>) = NetworkResponseCall(call)
}