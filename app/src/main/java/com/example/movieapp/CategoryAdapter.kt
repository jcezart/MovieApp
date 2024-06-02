package com.example.movieapp

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(private val itemList: List<String>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {

    }

}