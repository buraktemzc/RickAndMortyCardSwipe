package com.ebt.rickandmortycardswipe.data.repository.datasourceimpl

import com.ebt.rickandmortycardswipe.data.api.ApiService
import com.ebt.rickandmortycardswipe.data.model.APIResponse
import com.ebt.rickandmortycardswipe.data.repository.datasource.CharacterRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class CharacterRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : CharacterRemoteDataSource {

    override suspend fun getCharacters(page: Int): Response<APIResponse> {
        return apiService.getCharacters(page)
    }
}