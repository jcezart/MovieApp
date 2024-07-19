package com.example.movieapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class WatchListAdapter :
    ListAdapter<MovieEntity, WatchListAdapter.WatchViewHolder>(WatchDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_movie, parent, false)
        return WatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: WatchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class WatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imgBanner: ImageView = view.findViewById(R.id.movie_banner)
        private val tvTitle: TextView = view.findViewById(R.id.movie_title)
        private val tvReleaseDate: TextView = view.findViewById(R.id.movie_release_date)
        private val tvDuration: TextView = view.findViewById(R.id.movie_runtime)
        private val tvGenre: TextView = view.findViewById(R.id.movie_genre)
        private val tvRating: TextView = view.findViewById(R.id.movie_rating)

        fun bind(movieEntity: MovieEntity) {
            Glide.with(imgBanner.context)
                .load("https://image.tmdb.org/t/p/w500${movieEntity.posterPath}")
                .into(imgBanner)
            tvTitle.text = movieEntity.title
            tvReleaseDate.text = movieEntity.releaseDate
            tvDuration.text = movieEntity.runtime
            tvGenre.text = movieEntity.genres
            tvRating.text = movieEntity.rating.toString()
        }
    }

    class WatchDiffCallBack : DiffUtil.ItemCallback<MovieEntity>() {
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem == newItem
        }
    }
}
