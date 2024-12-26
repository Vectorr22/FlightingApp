package com.vector.flightingapp

import android.content.Context
import com.vector.flightingapp.data.AirportRepository
import com.vector.flightingapp.data.FavoriteRepository
import com.vector.flightingapp.data.OfflineAirportRepository
import com.vector.flightingapp.data.OfflineFavoriteRepository
import com.vector.flightingapp.data.room.AirportDatabase

interface AppContainer {
    val airportRepository: AirportRepository
    val favoriteRepository: FavoriteRepository
}

class AppDataContainer(private val context: Context): AppContainer{
    override val airportRepository: AirportRepository by lazy {
        OfflineAirportRepository(AirportDatabase.getDatabase(context).airportDao())
    }
    override val favoriteRepository: FavoriteRepository by lazy{
        OfflineFavoriteRepository(AirportDatabase.getDatabase(context).favoriteDao())
    }
}