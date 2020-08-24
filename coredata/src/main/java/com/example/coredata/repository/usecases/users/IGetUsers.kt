package com.example.coredata.repository.usecases.users

import com.example.coredata.models.User
import kotlinx.coroutines.flow.Flow

interface IGetUsers {
    suspend fun execute(): Flow<List<User>?>
}