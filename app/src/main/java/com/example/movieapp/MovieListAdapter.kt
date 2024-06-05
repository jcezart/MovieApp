package com.example.movieapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.GridView
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class MovieListAdapter:
    ListAdapter<MovieList, MovieListAdapter.MovieListViewHolder>(MovieListAdapter.ContactDiffUtils()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListAdapter.MovieListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_list, parent, false)
        return MovieListViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MovieListViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val gridMovie1= view.findViewById<ImageView>(R.id.imageView1)
        private val gridMovie2= view.findViewById<ImageView>(R.id.imageView2)
        private val gridMovie3= view.findViewById<ImageView>(R.id.imageView3)
        private val gridMovie4= view.findViewById<ImageView>(R.id.imageView4)

        fun bind(movieList: MovieList) {
            gridMovie1.setImageResource(movieList.imageResId)
            gridMovie2.setImageResource(movieList.imageResId)
            gridMovie3.setImageResource(movieList.imageResId)
            gridMovie4.setImageResource(movieList.imageResId)
        }
    }

    class ContactDiffUtils : DiffUtil.ItemCallback<MovieList>(){
        override fun areItemsTheSame(oldItem: MovieList, newItem: MovieList): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MovieList, newItem: MovieList): Boolean {
            return oldItem.imageResId == newItem.imageResId
        }
    }
}