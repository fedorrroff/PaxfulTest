package com.example.paxfultest.domain.interactors.storage

import com.example.paxfultest.domain.models.Joke
import com.example.paxfultest.domain.storage.PaxfulDB
import com.example.paxfultest.domain.storage.database.jokes.JokeObject
import javax.inject.Inject

class StorageInteractor @Inject constructor(
    private val database: PaxfulDB
): IStorageInteractor {

    override suspend fun addJokeToFavorites(joke: JokeObject) {
        database.getJokes().insert(joke)
    }

    override suspend fun getAllJokes(): List<JokeObject> {
        return database.getJokes().getAllJokes()
    }

    override suspend fun deleteJoke(id: Int) {
        return database.getJokes().deleteJoke(id)
    }

    override suspend fun getRandomJoke(): JokeObject? {
        return database.getJokes().getRandomJoke()
    }
}