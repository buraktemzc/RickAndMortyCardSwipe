package com.ebt.rickandmortycardswipe.data.repository.datasource

import com.ebt.rickandmortycardswipe.data.model.APIResponse
import retrofit2.Response

interface CharacterRemoteDataSource {
    suspend fun getCharacters(page: Int): Response<APIResponse>
}