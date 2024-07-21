package com.example.movieapp

import android.content.Context
import androidx.room.Room
import com.example.movieapp.network.MoviesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(context: Context) {

    private val db = Room.databaseBuilder(
        context,
        MovieAppDB::class.java, "movie-DB"
    ).build()

    suspend fun fetchAndSaveMovies(apiKey: String, category: String) {
        val moviesFromApi = fetchMoviesFromApi(apiKey, category)
        withContext(Dispatchers.IO) {
            val savedMovies = db.movieDao().getMoviesByCategory(category)
            if (savedMovies != moviesFromApi) {
                db.movieDao().deleteByCategory(category)
                db.movieDao().insertAll(moviesFromApi)
            }
        }
    }

    private suspend fun fetchMoviesFromApi(apiKey: String, category: String): List<MovieEntity> {
        return withContext(Dispatchers.IO) {
            val response = when (category) {
                "Now Playing" -> MoviesApi.retrofitService.getNowPlayingMovies(apiKey)
                "Upcoming" -> MoviesApi.retrofitService.getUpcomingMovies(apiKey)
                "Top Rated" -> MoviesApi.retrofitService.getTopRatedMovies(apiKey)
                "Popular" -> MoviesApi.retrofitService.getPopularMovies(apiKey)
                else -> null
            }
            val movies = response?.results ?: emptyList()
            movies.map { movieDetail ->
                MovieEntity(
                    id = movieDetail.id,
                    posterPath = movieDetail.poster_path ?: "",
                    backdropPath = movieDetail.backdrop_path ?: "",
                    title = movieDetail.title,
                    releaseDate = movieDetail.releaseDate ?: "",
                    runtime = movieDetail.runtime,
                    genres = movieDetail.genres?.joinToString(",") { it.name } ?:"",
                    rating = movieDetail.rating ?: 0f,
                    overview = movieDetail.overview ?: "",
                    category = category
                )
            }
        }
    }

    suspend fun getMoviesByCategory(category: String): List<MovieList> {
        return withContext(Dispatchers.IO) {
            val savedMovies = db.movieDao().getMoviesByCategory(category)
            if (savedMovies.isEmpty()) {
                fetchAndSaveMovies("334f18bf0d5802c21af75980ff872ada", category)
                db.movieDao().getMoviesByCategory(category)
            } else {
                savedMovies
            }
        }.map {
            MovieList(
                id = it.id,
                imageResIds = it.posterPath,
                poster_path = it.posterPath // Certifique-se de passar o parâmetro 'poster_path'
            )
        }
    }

    suspend fun saveMoviesToDatabase(movies: List<MovieEntity>) {
        withContext(Dispatchers.IO) {
            db.movieDao().insertAll(movies)
        }
    }

    suspend fun getAllMovies(): List<MovieEntity> {
        return withContext(Dispatchers.IO) {
            db.movieDao().getAll()
        }
    }

    suspend fun getFavoriteMovies(): List<MovieEntity> {
        return withContext(Dispatchers.IO){
            db.movieDao().getFavoriteMovies()
        }
    }

}
