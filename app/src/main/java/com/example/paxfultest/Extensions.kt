package com.example.paxfultest

import android.view.View
import com.example.paxfultest.domain.models.Joke
import com.example.paxfultest.domain.storage.database.jokes.JokeObject

object Extensions {

    fun View.makeGone() {
        this.visibility = View.GONE
    }

    fun View.makeVisible() {
        this.visibility = View.VISIBLE
    }

    fun View.makeInvisible() {
        this.visibility = View.INVISIBLE
    }

    fun Joke.toJokeObject(): JokeObject = JokeObject(
        joke = this.joke,
        id  = 0
    )

    fun JokeObject.toJoke(): Joke = Joke(
        id = this.id,
        joke = this.joke,
        categories = emptyList()
    )
}