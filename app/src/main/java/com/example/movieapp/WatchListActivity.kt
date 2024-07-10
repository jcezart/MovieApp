package com.example.movieapp


import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WatchListActivity : AppCompatActivity() {

    private lateinit var db: MovieAppDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.watch_list)

        db = Room.databaseBuilder(
            applicationContext,
            MovieAppDB::class.java, "movie-DB"
        ).build()

        val rvWatchList = findViewById<RecyclerView>(R.id.rv_watchList)
        val backButton3: ImageButton = findViewById(R.id.btn_nav_back3)
        val btnHome: ImageButton = findViewById(R.id.btn_home)

        val adapter4 = WatchListAdapter()
        rvWatchList.adapter = adapter4
        rvWatchList.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        loadFavorites(adapter4)

        backButton3.setOnClickListener {
            onBackPressed()
        }

        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadFavorites(adapter: WatchListAdapter) {
        lifecycleScope.launch(Dispatchers.IO) {
            val favoriteMovies = db.movieDao().getAll()
            withContext(Dispatchers.Main) {
                adapter.submitList(favoriteMovies)
            }
        }
    }
}
