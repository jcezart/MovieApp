package com.example.movieapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Query("SELECT * FROM movieentity")
    suspend fun getAll(): List<MovieEntity>

    @Query("SELECT * FROM movieentity WHERE category = :category")
    suspend fun getMoviesByCategory(category: String): List<MovieEntity>

    @Query("DELETE FROM movieentity WHERE category = :category")
    suspend fun deleteByCategory(category: String)
}
