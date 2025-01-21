package com.desafio.android.data.repository

import com.desafio.android.data.local.LogsDao
import com.desafio.android.data.mapper.LogsMapper
import com.desafio.android.data.model.Logs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface LogsRepository {
    suspend fun getLogsByCache(): Flow<List<Logs>>
}

class LogsRepositoryImpl(
    private val logsDao: LogsDao
) : LogsRepository {

    override suspend fun getLogsByCache(): Flow<List<Logs>> = logsDao.getAll().map {
        LogsMapper.transformToList(it)
    }
}