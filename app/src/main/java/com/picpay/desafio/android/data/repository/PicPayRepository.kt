package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.core.factory.toFlow
import com.picpay.desafio.android.core.repository.PicPayService
import com.picpay.desafio.android.data.local.UserDao
import com.picpay.desafio.android.data.mapper.UserDtoMapper
import com.picpay.desafio.android.data.mapper.UserMapper
import com.picpay.desafio.android.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface PicPayRepository {
    suspend fun getUsers(): Flow<List<User>>
    suspend fun getCachedUsers(): Flow<List<User>>

    suspend fun saveUsers(users : List<User>)
}
class PicPayRepositoryImpl(private val api: PicPayService,
                           private val userDao: UserDao
) : PicPayRepository {
    override suspend fun getUsers(): Flow<List<User>> =
        api.getUsers().toFlow().map {
            UserMapper.transformToList(it)
        }

    override suspend fun getCachedUsers(): Flow<List<User>> = userDao.getAll().map {
        UserMapper.transformToList(it)
    }

    override suspend fun saveUsers(users: List<User>) {
        userDao.deleteAll()
        userDao.insertAll(UserDtoMapper.transformToList(users))
    }

}