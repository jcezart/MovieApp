package com.example.movieapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.room.Room

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var adapter3: DetailCategoryAdapter
    private lateinit var movieOverview: TextView
    private lateinit var movieDetail: MovieDetailResponse
    private lateinit var db: MovieAppDB

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_details)

        supportActionBar?.hide()

        // Inicializa o banco de dados Room
        db = Room.databaseBuilder(
            applicationContext,
            MovieAppDB::class.java, "movie-DB"
        ).build()

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
        val favoriteButton: ImageButton = findViewById(R.id.btn_favorite)
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

        // Carregar detalhes do filme e configurar os botões
        lifecycleScope.launch {
            movieDetail = getMovieDetails(movieId)
            movieTitle.text = movieDetail.title
            movieOverview.text = movieDetail.overview
            movieReleaseDate.text = movieDetail.releaseDate
            movieDuration.text = getString(R.string.runtime_format, movieDetail.runtime)
            movieGenre.text = movieDetail.genres?.firstOrNull()?.name ?: "Unknown"
            movieRatio.text = String.format("%.1f", movieDetail.ratio)
            Glide.with(this@MovieDetailsActivity)
                .load("https://image.tmdb.org/t/p/w500${movieDetail.backdropPath}")
                .into(movieBanner)
            Glide.with(this@MovieDetailsActivity)
                .load("https://image.tmdb.org/t/p/w500${movieDetail.posterPath}")
                .into(movieMiniBanner)

            favoriteButton.setOnClickListener {
                saveToFavorites(movieDetail)
                val intent = Intent(this@MovieDetailsActivity, WatchListActivity::class.java)
                startActivity(intent)
            }
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

    private fun saveToFavorites(movieDetail: MovieDetailResponse) {
        // Adicionada a propriedade isFavorite ao MovieEntity
        val movieEntity = MovieEntity(
            id = movieDetail.id,
            posterPath = movieDetail.posterPath ?: "",
            backdropPath = movieDetail.backdropPath ?: "",
            title = movieDetail.title,
            releaseDate = movieDetail.releaseDate ?: "",
            runtime = movieDetail.runtime.toString(),
            genres = movieDetail.genres.joinToString { it.name },
            rating = movieDetail.ratio,
            overview = movieDetail.overview,
            category = "Favorite", // Adiciona uma categoria fixa "Favorite"
            isFavorite = true // Marca o filme como favorito
        )

        lifecycleScope.launch(Dispatchers.IO) {
            val existingMovie = db.movieDao().getMovieById(movieDetail.id)
            if (existingMovie != null) {
                existingMovie.isFavorite = true
                db.movieDao().update(existingMovie)
            } else {
            db.movieDao().insertAll(listOf(movieEntity))
        }
    }
}}

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
