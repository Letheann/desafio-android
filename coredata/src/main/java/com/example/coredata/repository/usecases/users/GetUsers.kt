package com.example.coredata.repository.usecases.users

import com.example.coredata.dao.db.UserDB
import com.example.coredata.extensions.safeRequestCheckingNetwork
import com.example.coredata.models.User
import com.example.coredata.repository.usecases.services.PicPayService
import com.example.coredata.utils.Constants
import com.example.coredata.utils.Utils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class GetUsers(
    private val service: PicPayService,
    private val utils: Utils,
    private val userDB: UserDB,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
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
    }.retry(Constants.RETRY) { e ->
        (e is Exception).also {
            if (it) delay(Constants.DELAY)
        }
    }.catch {
        emit(null)
    }.flowOn(dispatcher)
}

