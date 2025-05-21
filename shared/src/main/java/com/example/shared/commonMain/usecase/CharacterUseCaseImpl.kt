package com.example.shared.commonMain.usecase

import com.example.shared.commonMain.data.dto.HPCharacter
import com.example.shared.commonMain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

internal class CharacterUseCaseImpl(
    private val repository: CharacterRepository
) : CharacterUseCase {

    override fun getCharacters(): Flow<List<HPCharacter>> {
        return repository.getCharacters()
    }
}

interface CharacterUseCase {
    fun getCharacters(): Flow<List<HPCharacter>>
}
