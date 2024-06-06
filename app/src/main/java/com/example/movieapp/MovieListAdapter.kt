package com.example.movieapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieListAdapter : ListAdapter<MovieList, MovieListAdapter.MovieListViewHolder>(ContactDiffUtils()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_list, parent, false)
        return MovieListViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MovieListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.findViewById<ImageView>(R.id.imageView)
        /*private val imageView2 = view.findViewById<ImageView>(R.id.imageView2)
        private val imageView3 = view.findViewById<ImageView>(R.id.imageView3)
        private val imageView4 = view.findViewById<ImageView>(R.id.imageView4)*/

        fun bind(movieList: MovieList) {
            Glide.with(imageView.context).load(movieList.imageResIds).into(imageView)
            /*Glide.with(imageView2.context).load(movieList.imageResIds).into(imageView2)
            Glide.with(imageView3.context).load(movieList.imageResIds).into(imageView3)
            Glide.with(imageView4.context).load(movieList.imageResIds).into(imageView4)*/

        }
    }

    class ContactDiffUtils : DiffUtil.ItemCallback<MovieList>() {
        override fun areItemsTheSame(oldItem: MovieList, newItem: MovieList): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: MovieList, newItem: MovieList): Boolean {
            return oldItem.imageResIds == newItem.imageResIds
        }
    }
}
