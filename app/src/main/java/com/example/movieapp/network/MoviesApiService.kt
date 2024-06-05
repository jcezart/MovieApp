package com.example.movieapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query
import retrofit2.http.GET


private const val BASE_URL = "https://api.themoviedb.org/3/"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface MoviesApiService{
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("api_key")apiKey: String) : MovieResponse
}

object MoviesApi {
    val retrofitService : MoviesApiService by lazy {
        retrofit.create(MoviesApiService::class.java)
    }
}

