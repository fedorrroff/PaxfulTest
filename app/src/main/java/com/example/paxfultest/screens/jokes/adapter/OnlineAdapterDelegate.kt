package com.example.paxfultest.screens.jokes.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.paxfultest.domain.models.Joke
import com.example.paxfultest.screens.base.AdapterActionsListener
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class OnlineAdapterDelegate(
    private val offlineMode: Boolean,
    private val listener: AdapterActionsListener.JokesActionsListener
) : AdapterDelegate<MutableList<Joke?>>() {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        JokesViewHolder.create(
            parent,
            listener
        )

    override fun isForViewType(items: MutableList<Joke?>, position: Int): Boolean = !offlineMode

    override fun onBindViewHolder(
        items: MutableList<Joke?>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        (holder as JokesViewHolder).onBind(items[position])
    }
}