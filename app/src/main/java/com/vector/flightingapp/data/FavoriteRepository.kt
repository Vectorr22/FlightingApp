package com.vector.flightingapp.data

import com.vector.flightingapp.model.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun insertFavorite(favorite: Favorite)

    suspend fun getAllFavorites(): List<Favorite>

    suspend fun deleteFavorite(favorite: Favorite)

}