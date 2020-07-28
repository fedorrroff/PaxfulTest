package com.example.paxfultest.domain.interactors.api

import com.example.paxfultest.domain.models.Joke

interface IApiInteractor {

    suspend fun getJokesList(): List<Joke>
}