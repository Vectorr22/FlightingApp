package com.vector.flightingapp.data

import com.vector.flightingapp.data.room.AirportDao
import com.vector.flightingapp.model.Airport
import kotlinx.coroutines.flow.Flow

class OfflineAirportRepository(
    private val airportDao: AirportDao
) : AirportRepository {
    override fun getAllAirports(): Flow<List<Airport>> {
        return airportDao.getAllAirports()
    }

    override suspend fun insertItem(airport: Airport) {
        return airportDao.insertAirport(airport)
    }

    override suspend fun updateSearchList(search: String): List<Airport> {
        return airportDao.updateCurrentSearch(search)
    }

    override suspend fun getExcludedListOfAirports(iata_code: String): List<Airport> {
        return airportDao.getExcludedListOfAirports(iata_code)
    }


}