package com.example.paxfultest.screens.jokes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paxfultest.R
import com.example.paxfultest.domain.models.Joke
import com.example.paxfultest.screens.base.AdapterActionsListener

class JokesViewHolder
private constructor(
    itemView: View,
    private val listener: AdapterActionsListener.JokesActionsListener
) : RecyclerView.ViewHolder(itemView) {

    private val contentTextView = itemView.findViewById<TextView>(R.id.content_text_view)
    private val shareTextView = itemView.findViewById<TextView>(R.id.share_text_button)
    private val likeTextView = itemView.findViewById<TextView>(R.id.like_text_button)

    fun onBind(obj: Joke?) {
        contentTextView.text = obj?.joke
        shareTextView.setOnClickListener {
            listener.shareJoke(obj!!.joke)
        }
        likeTextView.setOnClickListener {
            listener.likeJoke(obj!!)
        }
    }

    companion object {

        @JvmStatic
        fun create(viewGroup: ViewGroup, listener: AdapterActionsListener.JokesActionsListener): JokesViewHolder {
            val itemView = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_single_joke, viewGroup, false)
            return JokesViewHolder(
                itemView,
                listener
            )
        }
    }
}