package com.example.coredata.repository.usecases.users

import com.example.coredata.models.User
import com.example.coredata.models.request.ViewEvents
import kotlinx.coroutines.flow.Flow

interface IGetUsers {
    suspend fun execute(): Flow<ViewEvents<List<User>>>
}