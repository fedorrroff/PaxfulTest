package com.example.paxfultest.domain.models

import com.google.gson.annotations.SerializedName

data class Joke(
    @SerializedName("id") val id: Int,
    @SerializedName("joke") val joke: String,
    @SerializedName("categories") val categories: List<String>
) {
}