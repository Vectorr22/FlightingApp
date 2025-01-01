package com.vector.flightingapp.data

import com.vector.flightingapp.model.Airport
import kotlinx.coroutines.flow.Flow

interface AirportRepository {
    fun getAllAirports(): Flow<List<Airport>>

    suspend fun insertItem(airport: Airport)

    suspend fun updateSearchList(search: String): List<Airport>

    suspend fun getExcludedListOfAirports(iata_code: String): List<Airport>
}