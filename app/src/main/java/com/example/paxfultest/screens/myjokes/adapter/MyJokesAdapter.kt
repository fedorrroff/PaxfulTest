package com.example.paxfultest.screens.myjokes.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.paxfultest.domain.models.Joke
import com.example.paxfultest.screens.base.AdapterActionsListener

class MyJokesAdapter(
    private val listener: AdapterActionsListener.MyJokesActionsListener
): RecyclerView.Adapter<MyJokesViewHolder>() {

    private val jokesList: MutableList<Joke> = mutableListOf()

    fun addJoke(newJoke: Joke) {
        jokesList.add(newJoke)
        notifyItemInserted(jokesList.size - 1)
    }

    fun setUpList(newList: List<Joke>) {
        jokesList.clear()
        jokesList.addAll(newList)
        notifyDataSetChanged()
    }

    fun removeItem(id: Int) {
        val position = jokesList.indexOfFirst {
            it.id == id
        }
        jokesList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, jokesList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyJokesViewHolder =
        MyJokesViewHolder.create(
            parent,
            listener
        )

    override fun getItemCount(): Int = jokesList.size

    override fun onBindViewHolder(holder: MyJokesViewHolder, position: Int) {
        holder.onBind(jokesList[position])
    }
}