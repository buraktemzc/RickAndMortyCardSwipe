package com.ebt.rickandmortycardswipe.domain.repository

import com.ebt.rickandmortycardswipe.data.model.APIResponse
import com.ebt.rickandmortycardswipe.data.util.Result

interface CharacterRepository {
    suspend fun getCharacters(page: Int): Result<APIResponse>
}