package com.example.paxfultest.screens.myjokes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.paxfultest.Extensions.toJoke
import com.example.paxfultest.Extensions.toJokeObject
import com.example.paxfultest.domain.interactors.storage.IStorageInteractor
import com.example.paxfultest.domain.models.Joke
import com.example.paxfultest.domain.models.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyJokesViewModel @Inject constructor(
    private val storageInteractor: IStorageInteractor
) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    fun getJokeList() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val jokesConverted: MutableList<Joke> = mutableListOf()
            storageInteractor.getAllJokes().forEach {
                jokesConverted.add(it.toJoke())
            }
            emit(Resource.success(data = jokesConverted))
        } catch (exception: Exception) {
            emit(
                Resource.error(
                    data = null,
                    message = exception.message ?: "Error getting exception message"
                )
            )
        }
    }

    fun deleteJoke(id: Int) {
        viewModelScope.launch {
            storageInteractor.deleteJoke(id)
        }
    }

    fun saveJoke(joke: Joke) {
        viewModelScope.launch {
            storageInteractor.addJokeToFavorites(joke.toJokeObject())
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}