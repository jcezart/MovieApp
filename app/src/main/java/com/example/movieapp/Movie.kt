package com.example.movieapp

import com.example.movieapp.network.Genre

data class Movie(
    val id: Int,
    val title: String,
    val poster_path: String,
    val overview: String,
    val backdrop_path: String?,
    val releaseDate: String,
    val runtime: String,
    val genres: List<Genre>,
    val rating: Float

)
