package com.vector.flightingapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "airport")
data class Airport(
    @PrimaryKey(autoGenerate = true)
        val id: Int=100,
    val name: String,
    val iata_code: String,
    val passengers: Long
)
