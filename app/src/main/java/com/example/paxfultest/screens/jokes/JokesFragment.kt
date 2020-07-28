package com.example.paxfultest.screens.jokes

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
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
import com.example.paxfultest.domain.models.Resource
import com.example.paxfultest.domain.models.Status
import com.example.paxfultest.screens.base.AdapterActionsListener
import com.example.paxfultest.screens.base.ShakeDetector
import com.example.paxfultest.screens.jokes.adapter.JokesAdapter
import kotlinx.android.synthetic.main.fragment_joke.*
import javax.inject.Inject


class JokesFragment : Fragment(),
    AdapterActionsListener.JokesActionsListener,
    AdapterActionsListener.MyJokesActionsListener {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    private val viewModel: JokesViewModel by lazy {
        ViewModelProvider(this, vmFactory).get(JokesViewModel::class.java)
    }

    private var mSensorManager: SensorManager? = null
    private var mAccelerometer: Sensor? = null
    private val mShakeDetector: ShakeDetector? = ShakeDetector()

    private lateinit var adapter: JokesAdapter

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
        return inflater.inflate(R.layout.fragment_joke, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mSensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mShakeDetector?.setMListener(createShakeListener())

        setToolbarTitle()

        adapter = JokesAdapter(this, viewModel.checkOfflineMode())
        rv_jokes.layoutManager = LinearLayoutManager(activity)
        rv_jokes.adapter = adapter

        viewModel.getJokes().observe(this, observeListChanges())
    }

    override fun onResume() {
        super.onResume()
        mSensorManager?.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        mSensorManager?.unregisterListener(mShakeDetector)
        super.onPause()
    }

    private fun setToolbarTitle() {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.jokes)
    }

    override fun shareJoke(joke: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, joke)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun likeJoke(joke: Joke) {
        viewModel.putLikedJokeToDb(joke)
    }

    override fun deleteJoke(id: Int) {
        viewModel.deleteJoke(id)
        adapter.removeItem(id)
    }

    private fun createShakeListener(): ShakeDetector.OnShakeListener =
        object : ShakeDetector.OnShakeListener {
            override fun onShake() {
                viewModel.getJokes().observe(this@JokesFragment, observeListChanges())
            }
        }

    private fun observeListChanges(): Observer<Resource<List<Joke?>>> =
        Observer<Resource<List<Joke?>>> {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        rv_jokes.makeVisible()
                        progress.makeGone()
                        resource.data?.let { data ->
                            adapter.setUpList(data)
                        }
                    }
                    Status.ERROR -> {
                        rv_jokes.makeInvisible()
                        progress.makeGone()
                        empty_list.makeVisible()
                    }
                    Status.LOADING -> {
                        progress.makeVisible()
                        rv_jokes.makeInvisible()
                        empty_list.makeGone()
                    }
                }
            }
        }

    companion object {
        fun newInstance() = JokesFragment()
    }
}
