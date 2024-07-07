package com.example.movieapp

import android.content.Context
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class WatchListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.watch_list)

        val rvWatchList = findViewById<RecyclerView>(R.id.rv_watchList)
        val backButton3: ImageButton = findViewById(R.id.btn_nav_back3)
        val btnHome: ImageButton = findViewById(R.id.btn_home)

        val adapter4 = WatchListAdapter()
        rvWatchList.adapter = adapter4
        rvWatchList.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        val favoriteMovies = intent.getParcelableArrayListExtra<WatchList>("FAVORITE_MOVIES")
        favoriteMovies?.let {adapter4.submitList(it) }

        backButton3.setOnClickListener {
            onBackPressed()
        }

        btnHome.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

}