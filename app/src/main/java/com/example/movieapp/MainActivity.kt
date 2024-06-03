package com.example.movieapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

    val rvCategory = findViewById<RecyclerView>(R.id.rv_categories)

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