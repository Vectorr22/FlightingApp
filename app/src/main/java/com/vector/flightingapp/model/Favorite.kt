package com.vector.flightingapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
        val id: Int,
    val departure_code: String,
    val destination_code: String
)
