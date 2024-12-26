package com.vector.flightingapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vector.flightingapp.model.Airport
import com.vector.flightingapp.model.Favorite

@Database(entities = [Airport::class, Favorite::class], version = 1, exportSchema = false)
abstract class AirportDatabase: RoomDatabase() {
    abstract fun airportDao(): AirportDao
    abstract fun favoriteDao(): FavoriteDao

    companion object{
        @Volatile
            private var instance: AirportDatabase? = null

        fun getDatabase(context: Context): AirportDatabase {
            return instance ?: synchronized(this){
                Room.databaseBuilder(context, AirportDatabase::class.java,"flight_search.db")
                    .createFromAsset("database/flight_search.db")
                    .build()
                    .also { instance = it }
            }
        }
    }

}