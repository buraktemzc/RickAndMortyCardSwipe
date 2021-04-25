package com.ebt.rickandmortycardswipe.domain.usecase

import com.ebt.rickandmortycardswipe.data.model.APIResponse
import com.ebt.rickandmortycardswipe.data.util.Result
import com.ebt.rickandmortycardswipe.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val characterRepository: CharacterRepository) {

    suspend fun execute(page: Int): Result<APIResponse> {
        return characterRepository.getCharacters(page)
    }
}