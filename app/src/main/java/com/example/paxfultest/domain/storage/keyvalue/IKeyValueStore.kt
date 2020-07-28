package com.example.paxfultest.domain.storage.keyvalue

interface IKeyValueStore {

    fun putString(key: String, value: String?)

    fun getString(key: String): String?

    fun putBoolean(key: String, value: Boolean)

    fun getBoolean(key: String): Boolean
}