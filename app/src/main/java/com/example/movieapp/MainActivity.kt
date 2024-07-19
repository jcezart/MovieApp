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
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var adapter2: MovieListAdapter
    private lateinit var repository: MovieRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        repository = MovieRepository(applicationContext)

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
            val movies = repository.getMoviesByCategory(category)
            adapter2.submitList(movies)
        }
    }
}

val categories = listOf(
    Category(name = "Now Playing"),
    Category(name = "Upcoming"),
    Category(name = "Top Rated"),
    Category(name = "Popular")
)
