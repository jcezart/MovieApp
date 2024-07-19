package com.example.movieapp

import android.os.Bundle
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

        repository = MovieRepository(applicationContext)

        val rvWatchList = findViewById<RecyclerView>(R.id.rv_watchList)
        adapter = WatchListAdapter()
        rvWatchList.adapter = adapter
        rvWatchList.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            val watchList = getWatchList()
            adapter.submitList(watchList)
        }
    }

    private suspend fun getWatchList(): List<MovieEntity> {
        return withContext(Dispatchers.IO) {
            repository.getAllMovies()
        }
    }
}
