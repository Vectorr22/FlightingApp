package com.vector.flightingapp.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class HistoryRepository(private val dataStore: DataStore<Preferences>) {
    private companion object{
        val HISTORY = stringPreferencesKey("history")
    }

    fun getHistory(): Flow<String>{
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[HISTORY] ?: ""
        }
    }

    suspend fun saveHistory(search: String){
        dataStore.edit { mutablePreferences ->
            mutablePreferences[HISTORY] = search
        }
    }
}