package com.example.movieapp.network

import com.google.gson.annotations.SerializedName

data class Genre(
    val id: Int,
    val name: String
)

data class MovieDetailResponse(
    val id: Int,
    val title: String,
    @SerializedName("backdrop_path") val backdropPath: String?,
    val overview: String,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("poster_path") val posterPath: String?,
    val runtime : Int,
    val genres: List<Genre>,
    @SerializedName("vote_average") val ratio: Float


)
