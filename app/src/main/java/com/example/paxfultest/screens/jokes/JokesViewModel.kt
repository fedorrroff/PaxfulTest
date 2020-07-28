package com.example.paxfultest.screens.jokes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.paxfultest.Extensions.toJoke
import com.example.paxfultest.Extensions.toJokeObject
import com.example.paxfultest.domain.interactors.api.IApiInteractor
import com.example.paxfultest.domain.interactors.storage.IStorageInteractor
import com.example.paxfultest.domain.models.Joke
import com.example.paxfultest.domain.models.Resource
import com.example.paxfultest.domain.storage.keyvalue.IKeyValueStore
import com.example.paxfultest.domain.storage.keyvalue.KeyValueStore
import kotlinx.coroutines.*
import javax.inject.Inject

class JokesViewModel @Inject constructor(
    private val apiInteractor: IApiInteractor,
    private val storageInteractor: IStorageInteractor,
    private val keyValueStore: IKeyValueStore
) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    fun getJokes() = liveData {
        emit(Resource.loading(data = null))
        try {
            emit(if (!checkOfflineMode()) {
                Resource.success(data = apiInteractor.getJokesList())
            } else {
                Resource.success(data = listOf(storageInteractor.getRandomJoke()?.toJoke()))
            })
        } catch (exception: Exception) {
            emit(
                Resource.error(
                    data = null,
                    message = exception.message ?: "Error getting exception message"
                )
            )
        }
    }

    fun putLikedJokeToDb(joke: Joke) {
        viewModelScope.launch {
            storageInteractor.addJokeToFavorites(joke.toJokeObject())
        }
    }

    fun deleteJoke(id: Int) {
        viewModelScope.launch {
            storageInteractor.deleteJoke(id)
        }
    }

    fun checkOfflineMode(): Boolean = keyValueStore.getBoolean(KeyValueStore.IS_OFFLINE)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}