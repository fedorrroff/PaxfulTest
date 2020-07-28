package com.example.paxfultest.screens.myjokes

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.paxfultest.R
import com.example.paxfultest.di.components.ComponentProvider
import com.example.paxfultest.domain.models.Joke
import com.google.android.material.textfield.TextInputEditText
import javax.inject.Inject

class NewJokeDialogFragment : DialogFragment() {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    private val viewModel: MyJokesViewModel by lazy {
        ViewModelProvider(this, vmFactory).get(MyJokesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ComponentProvider
            .provideApplicationComponent((activity as Context))
            .inject(this)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity as Context)
        // Get the layout inflater
        val inflater = activity!!.layoutInflater

        val dialogView = inflater.inflate(R.layout.dialog_add_joke, null)
        val etJokeContent = dialogView.findViewById<TextInputEditText>(R.id.joke_content_tv_dialog)

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(dialogView)
            // Add action buttons
            .setPositiveButton(
                "Save"
            ) { _, _ ->
                val joke = Joke(
                    id = 0,
                    joke = etJokeContent.text.toString(),
                    categories = listOf()
                )
                viewModel.saveJoke(joke)

                (parentFragment as MyJokesFragment).onJokeAdded(joke)
            }
            .setNegativeButton(
                "Close"
            ) { _, _ ->
                this.dialog?.cancel()
            }

        return builder.create()
    }
}