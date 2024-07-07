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

class WatchListAdapter:
    ListAdapter<WatchList, WatchListAdapter.WatchViewHolder>(WatchDiffCallBack()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchListAdapter.WatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_movie, parent, false)
        return WatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: WatchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class WatchViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val imgBanner: ImageView = view.findViewById(R.id.movie_banner)
        private val tvTitle: TextView = view.findViewById(R.id.movie_title)
        private val tvReleaseDate: TextView = view.findViewById(R.id.movie_release_date)
        private val tvDuration: TextView = view.findViewById(R.id.movie_runtime)
        private val tvGenre: TextView = view.findViewById(R.id.movie_genre)
        private val tvRating: TextView = view.findViewById(R.id.movie_rating)


        fun bind(watchList: WatchList) {
            Glide.with(imgBanner.context)
                .load("https://image.tmdb.org/t/p/w500${watchList.poster_path}")
                .into(imgBanner)
            tvTitle.text = watchList.title
            tvReleaseDate.text = watchList.release_date
            tvDuration.text = watchList.runtime
            tvGenre.text = watchList.genre
            tvRating.text = watchList.rating.toString()

        }
    }

    class WatchDiffCallBack : DiffUtil.ItemCallback<WatchList>(){
        override fun areItemsTheSame(oldItem: WatchList, newItem: WatchList): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WatchList, newItem: WatchList): Boolean {
            return oldItem == newItem
        }
    }




}