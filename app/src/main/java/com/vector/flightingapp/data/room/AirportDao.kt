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
}