package com.example.movieapp

import MovieViewModel
import MovieViewModelFactory
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var adapter2: MovieListAdapter
    private lateinit var repository: MovieRepository

    private val viewModel: MovieViewModel by viewModels {
        MovieViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        repository = MovieRepository(applicationContext)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val rvCategory = findViewById<RecyclerView>(R.id.rv_categories)
        val rvMovieList = findViewById<RecyclerView>(R.id.rv_movieList)
        val watchListButton = findViewById<ImageButton>(R.id.btn_favoriteList)
        val searchView = findViewById<SearchView>(R.id.sv_search)

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

        // Observe the ViewModel data
        viewModel.movies.observe(this, Observer {movies ->
            adapter2.submitList(movies)
        })

        // Set up the search view
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.searchMovies(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    viewModel.loadMoviesByCategory("Now Playing")
                } else {
                    viewModel.searchMovies(newText)
                }
                return true
            }
        })

        // Load "Now Playing" movies by default
        lifecycleScope.launch {
            viewModel.fetchAndSaveMovies("334f18bf0d5802c21af75980ff872ada", "Now Playing")
            viewModel.loadMoviesByCategory("Now Playing")

        }

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
