package com.vector.flightingapp

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.vector.flightingapp.data.datastore.HistoryRepository

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "setting"
)

class FlightingApplication : Application() {
    lateinit var container: AppContainer
    lateinit var historyRepository: HistoryRepository
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
        historyRepository = HistoryRepository(dataStore)
    }
}