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
import com.bumptech.glide.Glide

import com.example.movieapp.R

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
            Glide.with(gridMovie1.context).load(movieList.imageResId).into(gridMovie1)
            Glide.with(gridMovie2.context).load(movieList.imageResId).into(gridMovie2)
            Glide.with(gridMovie3.context).load(movieList.imageResId).into(gridMovie3)
            Glide.with(gridMovie4.context).load(movieList.imageResId).into(gridMovie4)
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