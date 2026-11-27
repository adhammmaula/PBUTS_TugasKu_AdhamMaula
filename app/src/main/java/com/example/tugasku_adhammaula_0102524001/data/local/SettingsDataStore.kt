package com.example.tugasku_adhammaula_0102524001.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings_pref")

class SettingsDataStore(private val context: Context) {
    companion object {
        val COMPACT_MODE_KEY = booleanPreferencesKey("mode_ringkas")
    }

    val isCompactMode: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[COMPACT_MODE_KEY] ?: false
        }

    suspend fun setCompactMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[COMPACT_MODE_KEY] = enabled
        }
    }
}