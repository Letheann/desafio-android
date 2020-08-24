package com.example.coredata.repository.usecases.users

import com.example.coredata.dao.db.UserDB
import com.example.coredata.extensions.safeRequestCheckingNetwork
import com.example.coredata.models.User
import com.example.coredata.repository.usecases.services.PicPayService
import com.example.coredata.utils.Utils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

open class GetUsers(
    private val service: PicPayService,
    private val utils: Utils,
    private val userDB: UserDB
) : IGetUsers {
    @ExperimentalCoroutinesApi
    override suspend fun execute(): Flow<List<User>?> = flow {
        emit(service.getUsers().run {
            this.safeRequestCheckingNetwork(utils)?.body().apply {
                if (this != null) {
                    userDB.insertAndDelete(this)
                }
            }
        })
    }.catch {
        emit(null)
    }
}

