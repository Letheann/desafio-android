package com.example.shared.commonMain.repository

import com.example.shared.commonMain.data.NetworkResponse
import com.example.shared.commonMain.data.dto.HPCharacter
import com.example.shared.commonMain.data.mapper.HPCharacterResponseMapper
import com.example.shared.commonMain.model.HPCharacterResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class CharacterRepositoryImpl(
    private val client: HttpClient
) : CharacterRepository {

    override fun getCharacters(): Flow<List<HPCharacter>> = flow {
        when (val response = fetchCharacters()) {
            is NetworkResponse.Success -> {
                emit(response.data.map { HPCharacterResponseMapper.transformTo(it) })
            }

            is NetworkResponse.Error -> {
                throw response.throwable
            }
        }
    }


    private suspend fun fetchCharacters(): NetworkResponse<List<HPCharacterResponse>> {
        return try {
            val result: List<HPCharacterResponse> =
                client.get("https://hp-api.onrender.com/api/characters").body()
            NetworkResponse.Success(result)
        } catch (e: Exception) {
            NetworkResponse.Error(e)
        }
    }
}


internal interface CharacterRepository {
    fun getCharacters(): Flow<List<HPCharacter>>
}
