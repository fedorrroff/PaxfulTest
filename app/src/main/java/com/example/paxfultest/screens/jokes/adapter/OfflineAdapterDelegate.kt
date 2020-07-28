package com.example.paxfultest.screens.jokes.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.paxfultest.domain.models.Joke
import com.example.paxfultest.screens.base.AdapterActionsListener
import com.example.paxfultest.screens.myjokes.adapter.MyJokesViewHolder
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class OfflineAdapterDelegate(
    private val offlineMode: Boolean,
    private val listener: AdapterActionsListener.MyJokesActionsListener
) : AdapterDelegate<MutableList<Joke?>>() {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        MyJokesViewHolder.create(parent, listener)

    override fun isForViewType(items: MutableList<Joke?>, position: Int): Boolean = offlineMode

    override fun onBindViewHolder(
        items: MutableList<Joke?>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        (holder as MyJokesViewHolder).onBind(items[position])
    }
}