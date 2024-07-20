package com.example.movieapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WatchListActivity : AppCompatActivity() {

    private lateinit var adapter: WatchListAdapter
    private lateinit var repository: MovieRepository



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.watch_list)

        repository = MovieRepository(this)

        val homeButton = findViewById<ImageButton>(R.id.btn_home)
        val rvWatchList = findViewById<RecyclerView>(R.id.rv_watchList)

        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        adapter = WatchListAdapter()
        rvWatchList.adapter = adapter
        rvWatchList.layoutManager = LinearLayoutManager(this)

        loadFavoriteMovies()
    }

    private fun loadFavoriteMovies() {
        lifecycleScope.launch {
            val favoriteMovies = repository.getAllMovies()
            adapter.submitList(favoriteMovies)
        }
    }
}
