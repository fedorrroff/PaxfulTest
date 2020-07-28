package com.example.paxfultest.domain.interactors.storage

import com.example.paxfultest.domain.storage.database.jokes.JokeObject

interface IStorageInteractor {

    suspend fun addJokeToFavorites(joke: JokeObject)

    suspend fun getRandomJoke() : JokeObject?

    suspend fun getAllJokes(): List<JokeObject>

    suspend fun deleteJoke(id: Int)

}