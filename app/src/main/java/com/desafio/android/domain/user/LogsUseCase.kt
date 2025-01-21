package com.desafio.android.domain.user

import com.desafio.android.data.model.Logs
import com.desafio.android.data.repository.LogsRepository
import kotlinx.coroutines.flow.Flow

interface LogsUseCase {
    suspend fun getLogsByCache(): Flow<List<Logs>>

}

class LogsUseCaseImpl(private val logsRepository: LogsRepository) : LogsUseCase {
    override suspend fun getLogsByCache(): Flow<List<Logs>> = logsRepository.getLogsByCache()

}