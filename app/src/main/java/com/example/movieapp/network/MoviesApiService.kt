package com.example.movieapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query
import retrofit2.http.GET
import retrofit2.http.Path


private const val BASE_URL = "https://api.themoviedb.org/3/"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface MoviesApiService{
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key")apiKey: String)
    : MovieResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key")apiKey: String)
            : MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key")apiKey: String)
            : MovieResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key")apiKey: String)
            : MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): MovieDetailResponse
}

object MoviesApi {
    val retrofitService : MoviesApiService by lazy {
        retrofit.create(MoviesApiService::class.java)
    }
}

