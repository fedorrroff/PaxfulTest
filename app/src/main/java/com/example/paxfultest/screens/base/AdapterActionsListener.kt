package com.example.paxfultest.screens.base

import com.example.paxfultest.domain.models.Joke

interface AdapterActionsListener {

    interface JokesActionsListener : AdapterActionsListener {

        fun shareJoke(joke: String)

        fun likeJoke(joke: Joke)
    }

    interface MyJokesActionsListener : AdapterActionsListener {

        fun deleteJoke(id: Int)
    }
}