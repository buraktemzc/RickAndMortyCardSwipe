package com.ebt.rickandmortycardswipe.domain.usecase

import com.ebt.rickandmortycardswipe.data.model.APIResponse
import com.ebt.rickandmortycardswipe.data.util.Result
import com.ebt.rickandmortycardswipe.domain.repository.CharacterRepository

class GetCharactersUseCase(private val characterRepository: CharacterRepository) {

    suspend fun execute(page: Int): Result<APIResponse> {
        return characterRepository.getCharacters(page)
    }
}