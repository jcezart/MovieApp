package com.example.movieapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.network.MoviesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    val rvCategory = findViewById<RecyclerView>(R.id.rv_categories)
    val rvMovieList = findViewById<RecyclerView>(R.id.rv_movieList)

        //setando o adapter
    val adapter1 = CategoryAdapter()
        rvCategory.adapter = adapter1
        rvCategory.layoutManager = LinearLayoutManager(this)
            .apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
        //submetendo a lista no adapter
        adapter1.submitList(categories)

    val adapter2 = MovieListAdapter()
        rvMovieList.adapter = adapter2
        rvMovieList.layoutManager = GridLayoutManager(this, 2)

        //chamada à API para obter os filmes e adicionar na RecyclerView
        lifecycleScope.launch {
            val movies = getMovies()
            adapter2.submitList(movies)
        }
    }

    private suspend fun getMovies(): List<MovieList> {
        return withContext(Dispatchers.IO){
            try {
                val apiKey = "334f18bf0d5802c21af75980ff872ada"
                val response = MoviesApi.retrofitService.getNowPlayingMovies(apiKey)
                val imageBaseUrl = "https://image.tmdb.org/t/p/w500"
                val movies = response.results.map { movie ->
                    MovieList(
                            "$imageBaseUrl${movie.poster_path}"
                        )
                }
                movies
            } catch (e:Exception) {
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
        name = "Top rated"
    ),
    Category(
        name = "Popular"
    )
)

/*val movies = listOf(
    MovieList(
        imageResId = R.drawable.movie_image1),
    MovieList(
        imageResId = R.drawable.movie_image2),
    MovieList(
        imageResId = R.drawable.movie_image3),
    MovieList(
        imageResId = R.drawable.movie_image4),
    )*/
