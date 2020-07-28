package com.example.paxfultest.domain.api

import com.example.paxfultest.domain.models.JokesResponce
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {

    @GET("/jokes/random/10")
    suspend fun getRandomJoke(): Response<JokesResponce>

    @GET("/jokes/random/10")
    suspend fun getRandomJokesWithName(
        @Query("firstName") firstName: String,
        @Query("lastName") lastName: String
    ): Response<JokesResponce>

}