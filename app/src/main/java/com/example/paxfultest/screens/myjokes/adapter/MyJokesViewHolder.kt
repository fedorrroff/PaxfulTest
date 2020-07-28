package com.example.paxfultest.screens.myjokes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paxfultest.R
import com.example.paxfultest.domain.models.Joke
import com.example.paxfultest.screens.base.AdapterActionsListener

class MyJokesViewHolder private constructor(
    itemView: View,
    private val listener: AdapterActionsListener.MyJokesActionsListener
) : RecyclerView.ViewHolder(itemView) {

    private val contentTextView = itemView.findViewById<TextView>(R.id.content_text_view_my_joke)
    private val deleteTextView = itemView.findViewById<TextView>(R.id.delete_text_button)

    fun onBind(obj: Joke?) {
        contentTextView.text = obj!!.joke

        deleteTextView.setOnClickListener {
            listener.deleteJoke(obj.id)
        }

    }

    companion object {

        @JvmStatic
        fun create(viewGroup: ViewGroup, listener: AdapterActionsListener.MyJokesActionsListener): MyJokesViewHolder {
            val itemView = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_my_joke, viewGroup, false)
            return MyJokesViewHolder(
                itemView,
                listener
            )
        }
    }
}