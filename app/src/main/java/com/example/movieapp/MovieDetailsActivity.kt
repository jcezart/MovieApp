package com.example.movieapp

import android.os.Bundle
import android.view.Window
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.network.Genre
import com.example.movieapp.network.MovieDetailResponse
import com.example.movieapp.network.MoviesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MovieDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_details)


        supportActionBar?.hide()

        val movieId = intent.getIntExtra("MOVIE_ID", 0)
        val movieBanner: ImageView = findViewById(R.id.img_banner)
        val movieMiniBanner: ImageView = findViewById(R.id.img_miniBanner)
        val movieTitle: TextView = findViewById(R.id.tv_movieTitle)
        val movieOverview: TextView = findViewById(R.id.tv_movieOverview)
        val movieReleaseDate: TextView = findViewById(R.id.tv_calendar)
        val movieDuration: TextView = findViewById(R.id.tv_clock)
        val movieGenre: TextView = findViewById(R.id.tv_ticket)
        val movieRatio: TextView = findViewById(R.id.tv_ratio)
        val toolbar = findViewById<Toolbar>(R.id.custom_toolbar)
        setSupportActionBar(findViewById(R.id.custom_toolbar))
        val backButton = toolbar.findViewById<ImageButton>(R.id.btn_nav_back)
        val rvDetailCategory = findViewById<RecyclerView>(R.id.rv_detailCategories)

        val adapter3 = DetailCategoryAdapter()
            rvDetailCategory.adapter = adapter3
            rvDetailCategory.layoutManager = LinearLayoutManager(this)
                .apply {
                    orientation = LinearLayoutManager.HORIZONTAL
                }
            adapter3.submitList(detailCategories)


        backButton.setOnClickListener {
            onBackPressed()
        }

        lifecycleScope.launch {
            val movieDetails = getMovieDetails(movieId)
            movieTitle.text = movieDetails.title
            movieOverview.text = movieDetails.overview
            movieReleaseDate.text = movieDetails.releaseDate
            movieDuration.text = getString(R.string.runtime_format, movieDetails.runtime)
            movieGenre.text = movieDetails.genres.firstOrNull()?.name ?: "Unknown"
            movieRatio.text = String.format("%.1f", movieDetails.ratio)
            Glide.with(this@MovieDetailsActivity)
                .load("https://image.tmdb.org/t/p/w500${movieDetails.backdropPath}")
                .into(movieBanner)
            Glide.with(this@MovieDetailsActivity)
                .load("https://image.tmdb.org/t/p/w500${movieDetails.posterPath}")
                .into(movieMiniBanner)

        }

    }

    private suspend fun getMovieDetails(movieId: Int): MovieDetailResponse {
        return withContext(Dispatchers.IO) {
            try {
                val apiKey = "334f18bf0d5802c21af75980ff872ada"
                MoviesApi.retrofitService.getMovieDetails(movieId, apiKey)
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle the error appropriately
                MovieDetailResponse(0, "", "", "", "", "", 0, emptyList(),0f)
            }
        }
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
