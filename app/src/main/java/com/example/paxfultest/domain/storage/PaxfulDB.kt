package com.example.paxfultest.domain.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.paxfultest.domain.storage.database.jokes.JokeObject
import com.example.paxfultest.domain.storage.database.jokes.JokesDao

@Database(
    entities = [JokeObject::class],
    version = 1,
    exportSchema = false
)
abstract class  PaxfulDB : RoomDatabase(){
    companion object {
        fun create(context: Context): PaxfulDB {
            val databaseBuilder = Room.databaseBuilder(context, PaxfulDB::class.java, "paxful.db")

            return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun getJokes(): JokesDao
}