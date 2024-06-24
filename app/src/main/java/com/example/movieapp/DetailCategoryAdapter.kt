package com.example.movieapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class DetailCategoryAdapter(
    private val onCategoryClick: (DetailCategory) -> Unit
) : ListAdapter<DetailCategory, DetailCategoryAdapter.CategoryViewHolder>(ContactDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvCategory: TextView = view.findViewById(R.id.tv_category)

        fun bind(category: DetailCategory) {
            tvCategory.text = category.name
            tvCategory.setOnClickListener {
                onCategoryClick(category)
            }
        }
    }

    class ContactDiffUtils : DiffUtil.ItemCallback<DetailCategory>() {
        override fun areItemsTheSame(oldItem: DetailCategory, newItem: DetailCategory): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: DetailCategory, newItem: DetailCategory): Boolean {
            return oldItem == newItem
        }
    }
}
