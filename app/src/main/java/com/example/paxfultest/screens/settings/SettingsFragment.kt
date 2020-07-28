package com.example.paxfultest.screens.settings

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.paxfultest.MainActivity
import com.example.paxfultest.R
import com.example.paxfultest.di.components.ComponentProvider
import com.example.paxfultest.domain.storage.keyvalue.KeyValueStore.Companion.FIRST_NAME
import com.example.paxfultest.domain.storage.keyvalue.KeyValueStore.Companion.LAST_NAME
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

class SettingsFragment : Fragment() {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    private val viewModel: SettingsViewModel by lazy {
        ViewModelProvider(this, vmFactory).get(SettingsViewModel::class.java)
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
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInputDoneListener()
        setSwitchChangeListener()
        setToolbarTitle()

        firstNameEt.setText(viewModel.getStringFromPrefs(FIRST_NAME), TextView.BufferType.EDITABLE)
        lastNameEt.setText(viewModel.getStringFromPrefs(LAST_NAME), TextView.BufferType.EDITABLE)

        firstNameEt.addTextChangedListener(getTextChangedListener(FIRST_NAME))
        lastNameEt.addTextChangedListener(getTextChangedListener(LAST_NAME))
    }

    private fun setSwitchChangeListener() {
        switch_online_offline.isChecked = viewModel.checkOfflineMode()
        switch_online_offline.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setSwitchChecked(isChecked)
        }
    }

    private fun setToolbarTitle() {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.settings)
    }

    private fun setInputDoneListener() {
        lastNameEt.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                lastNameEt.clearFocus()
                val imm: InputMethodManager = v.context
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun getTextChangedListener(key: String): TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s!!.isEmpty()) {
                viewModel.putStringToPrefs(null, key)
            } else {
                viewModel.putStringToPrefs(s.toString(), key)
            }
        }
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}