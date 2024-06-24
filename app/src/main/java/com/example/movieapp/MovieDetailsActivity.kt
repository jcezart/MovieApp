package com.example.movieapp

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var adapter3: DetailCategoryAdapter
    private lateinit var movieOverview: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_details)

        supportActionBar?.hide()

        val movieId = intent.getIntExtra("MOVIE_ID", 0)
        val movieBanner: ImageView = findViewById(R.id.img_banner)
        val movieMiniBanner: ImageView = findViewById(R.id.img_miniBanner)
        val movieTitle: TextView = findViewById(R.id.tv_movieTitle)
        movieOverview = findViewById(R.id.tv_movieOverview)
        val movieReleaseDate: TextView = findViewById(R.id.tv_calendar)
        val movieDuration: TextView = findViewById(R.id.tv_clock)
        val movieGenre: TextView = findViewById(R.id.tv_ticket)
        val movieRatio: TextView = findViewById(R.id.tv_ratio)
        setSupportActionBar(findViewById(R.id.custom_toolbar))
        val backButton: ImageButton = findViewById(R.id.btn_nav_back2)
        val rvDetailCategory = findViewById<RecyclerView>(R.id.rv_detailCategories)

        adapter3 = DetailCategoryAdapter { category ->
            loadDetailsByCategory(category.name, movieId)
        }

        rvDetailCategory.adapter = adapter3
        rvDetailCategory.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        adapter3.submitList(detailCategories)

        backButton.setOnClickListener {
            onBackPressed()
        }

        lifecycleScope.launch {
            loadMovieDetails(movieId)
        }
    }


    private fun loadDetailsByCategory(detailCategory: String, movieId: Int) {
        lifecycleScope.launch {
            val content = when (detailCategory) {
                "About Movie" -> {
                    val movieDetails = getMovieDetails(movieId)
                    movieDetails.overview
                }
                "Reviews" -> {
                    val movieReviews = getMovieReviews(movieId)
                    movieReviews?.results?.joinToString("\n") { it.content } ?: "No reviews available"
                }
                "Cast" -> {
                    val movieCast = getMovieCast(movieId)
                    movieCast.cast.joinToString("\n") { "${it.name} as ${it.character}" }
                }
                "Streaming" -> {
                    val movieStreaming = getMovieStreaming(movieId)
                    movieStreaming?.providers
                        ?.map { it.providerName }
                        ?.toSet()
                        ?.joinToString("\n") ?: "No streaming available"
                }
                else -> ""
            }
            movieOverview.text = content
        }
    }

    private suspend fun getMovieDetails(movieId: Int): MovieDetailResponse {
        return withContext(Dispatchers.IO) {
            try {
                val apiKey = "334f18bf0d5802c21af75980ff872ada"
                MoviesApi.retrofitService.getMovieDetails(movieId, apiKey)
            } catch (e: Exception) {
                e.printStackTrace()
                MovieDetailResponse(0, "", "", "", "", "", 0, emptyList(), 0f)
            }
        }
    }

    private suspend fun getMovieReviews(movieId: Int): MovieReviewListResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val apiKey = "334f18bf0d5802c21af75980ff872ada"
                MoviesApi.retrofitService.getMovieReviews(movieId, apiKey)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    private suspend fun getMovieCast(movieId: Int): MovieCastResponse {
        return withContext(Dispatchers.IO) {
            try {
                val apiKey = "334f18bf0d5802c21af75980ff872ada"
                MoviesApi.retrofitService.getMovieCast(movieId, apiKey)
            } catch (e: Exception) {
                e.printStackTrace()
                MovieCastResponse(emptyList())
            }
        }
    }

    private suspend fun getMovieStreaming(movieId: Int): StreamingDetail? {
        return withContext(Dispatchers.IO) {
            try {
                val apiKey = "334f18bf0d5802c21af75980ff872ada"
                MoviesApi.retrofitService.getMovieStreaming(movieId, apiKey).results["US"]
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    private suspend fun loadMovieDetails(movieId: Int) {
        val movieDetails = getMovieDetails(movieId)
        findViewById<TextView>(R.id.tv_movieTitle).text = movieDetails.title
        findViewById<TextView>(R.id.tv_movieOverview).text = movieDetails.overview
        findViewById<TextView>(R.id.tv_calendar).text = movieDetails.releaseDate
        findViewById<TextView>(R.id.tv_clock).text = getString(R.string.runtime_format, movieDetails.runtime)
        findViewById<TextView>(R.id.tv_ticket).text = movieDetails.genres.firstOrNull()?.name ?: "Unknown"
        findViewById<TextView>(R.id.tv_ratio).text = String.format("%.1f", movieDetails.ratio)
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movieDetails.backdropPath}")
            .into(findViewById(R.id.img_banner))
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movieDetails.posterPath}")
            .into(findViewById(R.id.img_miniBanner))
    }
}

val detailCategories = listOf(
    DetailCategory(
        name = "About Movie"
    ),
    DetailCategory(
        name = "Reviews"
    ),
    DetailCategory(
        name = "Cast"
    ),
    DetailCategory(
        name = "Streaming"
    )
)
