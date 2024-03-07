package com.picpay.desafio.android.domain

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.repository.PicPayRepository
import kotlinx.coroutines.flow.Flow

interface PicPayUseCase {
    suspend fun getUsers(): Flow<List<User>>
}

class PicPayUseCaseImpl(private val picPayRepository: PicPayRepository) : PicPayUseCase {
    override suspend fun getUsers(): Flow<List<User>> =
        picPayRepository.getUsers()

}