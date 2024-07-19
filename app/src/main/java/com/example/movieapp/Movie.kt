package com.example.movieapp

data class Movie(
    val id: Int,
    val title: String,
    val poster_path: String,
    val overview: String,
    val backdrop_path: String?,
    val releaseDate: String,
    val runtime: String,
    val genres: String,
    val rating: Float

)
