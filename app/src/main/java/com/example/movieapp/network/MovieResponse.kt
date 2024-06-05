package com.example.movieapp.network

import android.graphics.Movie

data class MovieResponse(
    val results: List<com.example.movieapp.network.Movie>
)

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String,
    val overview: String
)
