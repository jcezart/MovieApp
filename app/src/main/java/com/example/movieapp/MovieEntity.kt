// MovieEntity.kt
package com.example.movieapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieentity")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val posterPath: String,
    val backdropPath: String,
    val title: String,
    val releaseDate: String,
    val runtime: String,
    val genres: String,
    val rating: Float,
    val overview: String
)
