package com.example.coredata.repository.usecases

import com.example.coredata.extensions.safeRequestCheckingNetwork
import com.example.coredata.models.User
import com.example.coredata.repository.PicPayService
import com.example.coredata.utils.Utils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

open class GetUsers(
    private val service: PicPayService,
    private val utils: Utils
) {
    suspend fun execute(): Flow<List<User>?> = flow {
        emit(service.getUsers().run {
            this.safeRequestCheckingNetwork(utils)
        })
    }
}

