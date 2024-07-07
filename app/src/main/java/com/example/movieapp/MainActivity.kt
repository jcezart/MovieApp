package com.example.movieapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.lifecycleScope
import com.example.movieapp.network.MoviesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var adapter2: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val rvCategory = findViewById<RecyclerView>(R.id.rv_categories)
        val rvMovieList = findViewById<RecyclerView>(R.id.rv_movieList)
        val watchListButton = findViewById<ImageButton>(R.id.btn_favoriteList)

        watchListButton.setOnClickListener {
            val intent = Intent(this, WatchListActivity::class.java)
            startActivity(intent)
        }

        // Set up the category adapter
        val adapter1 = CategoryAdapter { category ->
            loadMoviesByCategory(category)
        }
        rvCategory.adapter = adapter1
        rvCategory.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        adapter1.submitList(categories)

        adapter2 = MovieListAdapter { movieId ->
            val intent = Intent(this, MovieDetailsActivity::class.java)
            intent.putExtra("MOVIE_ID", movieId)
            startActivity(intent)
        }
        rvMovieList.adapter = adapter2
        rvMovieList.layoutManager = GridLayoutManager(this, 2)

        // Load "Now Playing" movies by default
        loadMoviesByCategory("Now Playing")
    }

    private fun loadMoviesByCategory(category: String) {
        lifecycleScope.launch {
            val movies = when (category) {
                "Now Playing" -> getNowPlayingMovies()
                "Upcoming" -> getUpcomingMovies()
                "Top Rated" -> getTopRatedMovies()
                "Popular" -> getPopularMovies()
                else -> emptyList()
            }
            adapter2.submitList(movies)
        }
    }

    private suspend fun getNowPlayingMovies(): List<MovieList> {
        return withContext(Dispatchers.IO) {
            try {
                val apiKey = "334f18bf0d5802c21af75980ff872ada"
                val response = MoviesApi.retrofitService.getNowPlayingMovies(apiKey)
                val imageBaseUrl = "https://image.tmdb.org/t/p/w500"
                response.results.map { movie ->
                    MovieList(
                        id = movie.id,
                        "$imageBaseUrl${movie.poster_path}"
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }

    private suspend fun getUpcomingMovies(): List<MovieList> {
        return withContext(Dispatchers.IO) {
            try {
                val apiKey = "334f18bf0d5802c21af75980ff872ada"
                val response = MoviesApi.retrofitService.getUpcomingMovies(apiKey)
                val imageBaseUrl = "https://image.tmdb.org/t/p/w500"
                response.results.map { movie ->
                    MovieList(
                        id = movie.id,
                        "$imageBaseUrl${movie.poster_path}"
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }

    private suspend fun getTopRatedMovies(): List<MovieList> {
        return withContext(Dispatchers.IO) {
            try {
                val apiKey = "334f18bf0d5802c21af75980ff872ada"
                val response = MoviesApi.retrofitService.getTopRatedMovies(apiKey)
                val imageBaseUrl = "https://image.tmdb.org/t/p/w500"
                response.results.map { movie ->
                    MovieList(
                        id = movie.id,
                        "$imageBaseUrl${movie.poster_path}"
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }

    private suspend fun getPopularMovies(): List<MovieList> {
        return withContext(Dispatchers.IO) {
            try {
                val apiKey = "334f18bf0d5802c21af75980ff872ada"
                val response = MoviesApi.retrofitService.getPopularMovies(apiKey)
                val imageBaseUrl = "https://image.tmdb.org/t/p/w500"
                response.results.map { movie ->
                    MovieList(
                        id = movie.id,
                        "$imageBaseUrl${movie.poster_path}"
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }
}

val categories = listOf(
    Category(
        name = "Now Playing"
    ),
    Category(
        name = "Upcoming"
    ),
    Category(
        name = "Top Rated"
    ),
    Category(
        name = "Popular"
    )
)
