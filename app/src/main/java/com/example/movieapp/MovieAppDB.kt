// MovieAppDB.kt
package com.example.movieapp

import MovieDAO
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieAppDB : RoomDatabase() {
    abstract fun movieDao(): MovieDAO
}
