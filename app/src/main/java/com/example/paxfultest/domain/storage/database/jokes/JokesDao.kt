package com.example.paxfultest.domain.storage.database.jokes

import androidx.room.*

@Dao
interface JokesDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(jokes: JokeObject)

    @Transaction
    @Query("SELECT * FROM jokes")
    suspend fun getAllJokes(): List<JokeObject>

    @Transaction
    @Query("DELETE FROM jokes WHERE id IS :idToDelete")
    suspend fun deleteJoke(idToDelete: Int)

    @Transaction
    @Query("SELECT * FROM jokes ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomJoke(): JokeObject?
}