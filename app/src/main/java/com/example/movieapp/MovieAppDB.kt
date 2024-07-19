// MovieAppDB.kt
package com.example.movieapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 2)
abstract class MovieAppDB : RoomDatabase() {
    abstract fun movieDao(): MovieDAO
}
