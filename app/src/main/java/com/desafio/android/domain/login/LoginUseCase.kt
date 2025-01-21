package com.desafio.android.domain.login

import com.desafio.android.data.repository.LoginRepository
import com.desafio.android.presentation.login.compose.Credentials
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {
    fun encrypt(credentials: Credentials): Flow<ByteArray>
    fun decrypt(): Flow<String>
}

class LoginUseCaseImpl(private val repository: LoginRepository) : LoginUseCase {
    override fun encrypt(credentials: Credentials): Flow<ByteArray> =
        repository.encrypt(credentials)

    override fun decrypt(): Flow<String> = repository.decrypt()


}