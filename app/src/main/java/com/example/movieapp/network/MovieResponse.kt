package com.example.movieapp.network


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val results: List<Movie>
)

data class Movie(
    val id: Int,
    val title: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("runtime") val runtime: String?,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("vote_average") val ratio: Float,
    val overview: String
)


