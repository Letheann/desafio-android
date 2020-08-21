package com.picpay.desafio.android.repository.usecases

import com.picpay.desafio.android.helper.extensions.safeRequestCheckingNetwork
import com.picpay.desafio.android.models.User
import com.picpay.desafio.android.repository.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class GetUsers {
    suspend fun execute(): List<User>? = withContext(Dispatchers.IO) {
        RetrofitBuilder().service.getUsers().safeRequestCheckingNetwork()
    }
}