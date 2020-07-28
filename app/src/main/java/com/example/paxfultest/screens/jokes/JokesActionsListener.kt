package com.example.paxfultest.screens.jokes

import com.example.paxfultest.domain.models.Joke

interface JokesActionsListener {

    fun shareJoke(joke: String)

    fun likeJoke(joke: Joke)
}