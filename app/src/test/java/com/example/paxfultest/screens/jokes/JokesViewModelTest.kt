package com.example.paxfultest.screens.jokes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.paxfultest.Extensions.toJokeObject
import com.example.paxfultest.domain.interactors.api.IApiInteractor
import com.example.paxfultest.domain.interactors.storage.IStorageInteractor
import com.example.paxfultest.domain.models.Joke
import com.example.paxfultest.domain.models.Resource
import com.example.paxfultest.domain.storage.keyvalue.IKeyValueStore
import com.example.paxfultest.domain.storage.keyvalue.KeyValueStore.Companion.IS_OFFLINE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class JokesViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock private lateinit var apiInteractor: IApiInteractor
    @Mock private lateinit var storageInteractor: IStorageInteractor
    @Mock private lateinit var keyValueStore: IKeyValueStore

    @Mock
    private lateinit var apiUsersObserver: Observer<Resource<List<Joke?>>>

    private lateinit var sut: JokesViewModel

    @Before
    fun setUp() {
        sut = JokesViewModel(apiInteractor, storageInteractor, keyValueStore)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getJokes in online mode should call getJokesList method`() {
        `when`(keyValueStore.getBoolean(IS_OFFLINE)).thenReturn(false)
        testCoroutineRule.runBlockingTest {
                `when`(apiInteractor.getJokesList())
                .thenReturn(emptyList<Joke>())
            sut.getJokes().observeForever(apiUsersObserver)
            verify(apiInteractor).getJokesList()
            sut.getJokes().removeObserver(apiUsersObserver)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getJokes in offline mode should call getRandomJoke from DB method` () {
        `when`(keyValueStore.getBoolean(IS_OFFLINE)).thenReturn(true)
        testCoroutineRule.runBlockingTest {
            sut.getJokes().observeForever(apiUsersObserver)
            verify(storageInteractor).getRandomJoke()
            sut.getJokes().removeObserver(apiUsersObserver)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `start loading should emit LOADING event`() {
        `when`(keyValueStore.getBoolean(IS_OFFLINE)).thenReturn(true)
        testCoroutineRule.runBlockingTest {
            sut.getJokes().observeForever(apiUsersObserver)
            verify(apiUsersObserver).onChanged(Resource.loading(null))
            sut.getJokes().removeObserver(apiUsersObserver)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `start loading should emit SUCESS event if got data`() {
        `when`(keyValueStore.getBoolean(IS_OFFLINE)).thenReturn(false)
        testCoroutineRule.runBlockingTest {
            `when`(apiInteractor.getJokesList())
                .thenReturn(emptyList<Joke>())
            sut.getJokes().observeForever(apiUsersObserver)
            verify(apiUsersObserver).onChanged(Resource.success(emptyList()))
            sut.getJokes().removeObserver(apiUsersObserver)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `start loading should emit ERROR event if got exception`() {
        `when`(keyValueStore.getBoolean(IS_OFFLINE)).thenReturn(false)
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message For You"
            `when`(apiInteractor.getJokesList())
                .thenThrow(RuntimeException(errorMessage))
            sut.getJokes().observeForever(apiUsersObserver)
            verify(apiUsersObserver).onChanged(Resource.error(null, errorMessage))
            sut.getJokes().removeObserver(apiUsersObserver)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `putLikedJokeToDb should call addJokeToFavorites from storageInteractor`() {
        testCoroutineRule.runBlockingTest {
            val joke = Joke(0, "a", listOf())
            sut.putLikedJokeToDb(joke)
            verify(storageInteractor).addJokeToFavorites(joke.toJokeObject())
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `deleteJoke should call deleteJoke from storageInteractor`() {
        testCoroutineRule.runBlockingTest {
            val id = 0
            sut.deleteJoke(id)
            verify(storageInteractor).deleteJoke(id)
        }
    }

    @After
    fun tearDown() {
        reset(storageInteractor)
    }

}