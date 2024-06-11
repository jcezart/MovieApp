package com.example.movieapp

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.movieapp.network.MovieDetailResponse
import com.example.movieapp.network.MoviesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MovieDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_details)

        val movieId = intent.getIntExtra("MOVIE_ID", 0)
        val movieBanner: ImageView = findViewById(R.id.img_banner)
        val movieTitle: TextView = findViewById(R.id.tv_movieTitle)
        val movieOverview: TextView = findViewById(R.id.tv_movieOverview)
        val toolbar = findViewById<Toolbar>(R.id.custom_toolbar)
        setSupportActionBar(findViewById(R.id.custom_toolbar))
        val backButton = toolbar.findViewById<ImageButton>(R.id.btn_nav_back)


        backButton.setOnClickListener {
            onBackPressed()
        }


        lifecycleScope.launch {
            val movieDetails = getMovieDetails(movieId)
            movieTitle.text = movieDetails.title
            movieOverview.text = movieDetails.overview
            Glide.with(this@MovieDetailsActivity)
                .load("https://image.tmdb.org/t/p/w500${movieDetails.backdropPath}")
                .into(movieBanner)
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
                MovieDetailResponse(0, "", "", "", "", "")
            }
        }
    }
}
