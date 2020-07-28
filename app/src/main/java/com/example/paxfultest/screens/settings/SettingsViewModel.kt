package com.example.paxfultest.screens.settings

import androidx.lifecycle.ViewModel
import com.example.paxfultest.domain.storage.keyvalue.IKeyValueStore
import com.example.paxfultest.domain.storage.keyvalue.KeyValueStore.Companion.IS_OFFLINE
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val keyValueStore: IKeyValueStore
) : ViewModel() {

    fun setSwitchChecked(checked: Boolean) = keyValueStore.putBoolean(IS_OFFLINE, checked)

    fun checkOfflineMode(): Boolean = keyValueStore.getBoolean(IS_OFFLINE)

    fun putStringToPrefs(value: String?, key: String) {
        keyValueStore.putString(key, value)
    }

    fun getStringFromPrefs(key: String): String = keyValueStore.getString(key) ?: ""

}