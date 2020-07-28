package com.example.paxfultest.domain.interactors.api

import com.example.paxfultest.domain.api.APIInterface
import com.example.paxfultest.domain.interactors.api.IApiInteractor
import com.example.paxfultest.domain.models.Joke
import com.example.paxfultest.domain.storage.keyvalue.IKeyValueStore
import com.example.paxfultest.domain.storage.keyvalue.KeyValueStore.Companion.FIRST_NAME
import com.example.paxfultest.domain.storage.keyvalue.KeyValueStore.Companion.LAST_NAME
import javax.inject.Inject

class ApiInteractor @Inject constructor(
    private val api: APIInterface,
    private val keyValueStore: IKeyValueStore
) : IApiInteractor {

    override suspend fun getJokesList(): List<Joke> {
        val firstName = keyValueStore.getString(FIRST_NAME) ?: "Chuck"
        val lastName = keyValueStore.getString(LAST_NAME) ?: "Norris"

        val response = api.getRandomJokesWithName(firstName, lastName)
        if (response.isSuccessful) {
            return response.body()!!.value
        }

        return emptyList()
    }
}