package com.ebt.rickandmortycardswipe.data.repository

import com.ebt.rickandmortycardswipe.data.model.APIResponse
import com.ebt.rickandmortycardswipe.data.repository.datasource.CharacterRemoteDataSource
import com.ebt.rickandmortycardswipe.data.util.Result
import com.ebt.rickandmortycardswipe.domain.repository.CharacterRepository
import retrofit2.Response
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterRemoteDataSource: CharacterRemoteDataSource
) : CharacterRepository {

    override suspend fun getCharacters(page: Int): Result<APIResponse> {
        return convertToResult(characterRemoteDataSource.getCharacters(page))
    }

    private fun convertToResult(response: Response<APIResponse>): Result<APIResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Result.Success(it)
            }
        }
        return Result.Error(response.message())
    }
}