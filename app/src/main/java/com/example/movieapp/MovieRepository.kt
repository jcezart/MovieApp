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
            // Obtenha os favoritos atuais antes de deletar os filmes pela categoria
            val currentFavorites = db.movieDao().getFavoriteMovies().map { it.id }
            // Filtre os filmes obtidos da API para não sobrescrever o status de favorito
            val moviesToSave = moviesFromApi.map { movie ->
                if (currentFavorites.contains(movie.id)) {
                    movie.copy(isFavorite = true)
                } else {
                    movie
                }
            }
            db.movieDao().deleteByCategory(category)
            db.movieDao().insertAll(moviesToSave)
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
                    posterPath = movieDetail.posterPath ?: "",
                    backdropPath = movieDetail.backdropPath ?: "",
                    title = movieDetail.title,
                    releaseDate = movieDetail.releaseDate ?: "",
                    runtime = movieDetail.runtime.toString(),
                    genres = movieDetail.genres?.joinToString(",") { it.name } ?:"",
                    rating = movieDetail.ratio ?: 0f,
                    overview = movieDetail.overview ?: "",
                    category = category
                )
            }
        }
    }

    suspend fun getMoviesByCategory(category: String): List<MovieEntity> {
        return withContext(Dispatchers.IO) {
            val savedMovies = db.movieDao().getMoviesByCategory(category)
            if (savedMovies.isEmpty()) {
                fetchAndSaveMovies("334f18bf0d5802c21af75980ff872ada", category)
                db.movieDao().getMoviesByCategory(category)
            } else {
                savedMovies
            }
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

    suspend fun searchMovies(query: String): List<MovieEntity> {
        return withContext(Dispatchers.IO) {
            db.movieDao().searchMoviesByTitle("%$query%")
        }
    }
}
