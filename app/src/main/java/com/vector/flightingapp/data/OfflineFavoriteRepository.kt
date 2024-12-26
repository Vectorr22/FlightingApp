package com.vector.flightingapp.data

import com.vector.flightingapp.data.room.FavoriteDao
import com.vector.flightingapp.model.Favorite
import kotlinx.coroutines.flow.Flow

class OfflineFavoriteRepository(
    private val favoriteDao: FavoriteDao
): FavoriteRepository {
    override suspend fun insertFavorite(favorite: Favorite) {
        return favoriteDao.insertFavorite(favorite)
    }

    override fun getAllFavorites(): Flow<List<Favorite>> {
        return favoriteDao.getAllFavorites()
    }
}