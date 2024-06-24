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


data class MovieReviewListResponse(
    val id: Int,
    val results: List<MovieReviewResponse>
)

data class MovieReviewResponse(
    val id: Int,
    val author: String,
    val content: String,
    val rating: Int?
)

data class MovieCastResponse(
    val cast: List<CastResponse>
)

data class CastResponse(
    val id: Int,
    val name: String,
    val character: String,
    @SerializedName("profile_path") val profilePath: String?
)

data class StreamingDetail(
    @SerializedName("link") val link: String?,
    @SerializedName("flatrate") val providers: List<Provider>?
)

data class Provider(
    @SerializedName("provider_id") val providerId: Int,
    @SerializedName("provider_name") val providerName: String,
    @SerializedName("logo_path") val logoPath: String?
)

data class StreamingResponse(
    val results: Map<String, StreamingDetail>
)


