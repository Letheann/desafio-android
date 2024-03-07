package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.core.repository.PicPayService
import com.picpay.desafio.android.core.factory.toFlow
import com.picpay.desafio.android.data.mapper.UserMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface PicPayRepository {
    suspend fun getUsers(): Flow<List<User>>
}
class PicPayRepositoryImpl(private val api: PicPayService) : PicPayRepository {
    override suspend fun getUsers(): Flow<List<User>> =
        api.getUsers().toFlow().map {
            UserMapper.transformToList(it)
        }
}