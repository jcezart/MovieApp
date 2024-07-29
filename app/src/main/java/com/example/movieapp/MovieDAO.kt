package com.example.movieapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

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

    @Query("SELECT * FROM movieentity WHERE isFavorite = 1")
    suspend fun getFavoriteMovies(): List<MovieEntity>

    @Query("SELECT * FROM movieentity WHERE title LIKE :query")
    suspend fun searchMoviesByTitle(query: String): List<MovieEntity>

    @Query("SELECT * FROM movieentity WHERE id = :movieId LIMIT 1")
    suspend fun getMovieById(movieId: Int): MovieEntity?

    @Update
    suspend fun update(movie: MovieEntity)
}
