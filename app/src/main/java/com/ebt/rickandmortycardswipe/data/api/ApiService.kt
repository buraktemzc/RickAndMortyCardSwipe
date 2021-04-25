package com.ebt.rickandmortycardswipe.data.api

import com.ebt.rickandmortycardswipe.data.model.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("character")
    suspend fun getCharacters(
        @Query("page")
        page: Int
    ): Response<APIResponse>

}