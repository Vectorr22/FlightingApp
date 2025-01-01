package com.vector.flightingapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vector.flightingapp.model.Airport
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAirport(airport: Airport)

    @Query("SELECT * FROM airport")
    fun getAllAirports(): Flow<List<Airport>>

    @Query("SELECT * FROM airport WHERE name LIKE '%'||:search||'%' UNION ALL SELECT * FROM airport WHERE iata_code LIKE '%'||:search||'%'")
    suspend fun updateCurrentSearch(search: String): List<Airport>

    @Query("SELECT * FROM airport WHERE iata_code NOT LIKE :iata_code")
    suspend fun getExcludedListOfAirports(iata_code: String): List<Airport>
}