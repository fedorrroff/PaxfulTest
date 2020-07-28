package com.example.paxfultest.screens.myjokes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paxfultest.Extensions.makeGone
import com.example.paxfultest.Extensions.makeInvisible
import com.example.paxfultest.Extensions.makeVisible
import com.example.paxfultest.MainActivity
import com.example.paxfultest.R
import com.example.paxfultest.di.components.ComponentProvider
import com.example.paxfultest.domain.models.Joke
import com.example.paxfultest.domain.models.Status
import com.example.paxfultest.screens.base.AdapterActionsListener
import com.example.paxfultest.screens.myjokes.adapter.MyJokesAdapter
import kotlinx.android.synthetic.main.fragment_my_jokes.*
import javax.inject.Inject

class MyJokesFragment : Fragment(),
    AdapterActionsListener.MyJokesActionsListener,
    AdapterActionsListener.JokesActionsListener {

    private val adapter: MyJokesAdapter =
        MyJokesAdapter(this)

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_jokes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle()

        rv_my_jokes.layoutManager = LinearLayoutManager(activity)
        rv_my_jokes.adapter = adapter

        viewModel.getJokeList().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        rv_my_jokes.makeVisible()
                        progress_my_jokes.makeGone()
                        resource.data?.let { data ->
                            adapter.setUpList(data)
                        }
                    }
                    Status.ERROR -> {
                        rv_my_jokes.makeInvisible()
                        progress_my_jokes.makeGone()
                        empty_list_my_jokes.makeVisible()
                    }
                    Status.LOADING -> {
                        progress_my_jokes.makeVisible()
                        rv_my_jokes.makeInvisible()
                        empty_list_my_jokes.makeGone()
                    }
                }
            }
        })

        fab_my_jokes.setOnClickListener {
            val newJokeDialogFragment = NewJokeDialogFragment()
            newJokeDialogFragment.show(childFragmentManager, TAG)
        }
    }

    fun onJokeAdded(joke: Joke) {
        adapter.addJoke(joke)
        rv_my_jokes.scrollToPosition(adapter.itemCount - 1)
    }

    override fun deleteJoke(id: Int) {
        viewModel.deleteJoke(id)
        adapter.removeItem(id)
    }

    override fun shareJoke(joke: String) {
        //not needed
    }

    override fun likeJoke(joke: Joke) {
        viewModel.saveJoke(joke)
    }

    private fun setToolbarTitle() {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.my_jokes)
    }

    companion object {
        const val TAG = "MyJokesFragment"

        fun newInstance(): MyJokesFragment = MyJokesFragment()
    }
}