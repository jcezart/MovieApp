package com.example.movieapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WatchList(
    val id: Int,
    val poster_path: String,
    val title: String,
    val release_date: String,
    val runtime: String?,
    val genre: String,
    val rating: Float
) : Parcelable
