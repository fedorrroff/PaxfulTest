package com.example.paxfultest.domain.storage.keyvalue

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class KeyValueStore
@Inject constructor(val context: Context) : IKeyValueStore {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(
            UNIQUE_KEY,
            Context.MODE_PRIVATE
        )
    }

    override fun putString(key: String, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    override fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    override fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    companion object {
        const val UNIQUE_KEY = "UNIQUE_KEY"
        const val IS_OFFLINE = "IS_OFFLINE"
        const val FIRST_NAME = "FIRST_NAME"
        const val LAST_NAME = "LAST_NAME"
    }
}