package com.ebt.rickandmortycardswipe.data.model


import com.google.gson.annotations.SerializedName

data class APIResponse(
    @SerializedName("info")
    val info: Info?,
    @SerializedName("results")
    val characters: List<Character>?
)