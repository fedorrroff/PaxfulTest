package com.example.paxfultest.screens.jokes.adapter

import com.example.paxfultest.domain.models.Joke
import com.example.paxfultest.screens.base.AdapterActionsListener
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class JokesAdapter(
    listener: AdapterActionsListener,
    offlineMode: Boolean
) : ListDelegationAdapter<MutableList<Joke?>>() {

    private val jokesList: MutableList<Joke?> = mutableListOf()

    init {
        this.items = jokesList
        delegatesManager
            .addDelegate(OnlineAdapterDelegate(offlineMode, (listener as AdapterActionsListener.JokesActionsListener)))
            .addDelegate(OfflineAdapterDelegate(offlineMode, (listener as AdapterActionsListener.MyJokesActionsListener)))
    }

    fun setUpList(newList: List<Joke?>) {
        jokesList.clear()
        jokesList.addAll(newList)
        notifyDataSetChanged()
    }

    fun removeItem(id: Int) {
        val position = jokesList.indexOfFirst {
            it!!.id == id
        }
        jokesList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, jokesList.size)
    }

    override fun getItemCount(): Int = jokesList.size

}