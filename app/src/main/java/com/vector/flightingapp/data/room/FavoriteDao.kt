package com.vector.flightingapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.vector.flightingapp.model.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert
    suspend fun insertFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): Flow<List<Favorite>>

}