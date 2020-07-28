package com.example.paxfultest.domain.storage.database.jokes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jokes")
data class JokeObject(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "joke")
    val joke: String
)