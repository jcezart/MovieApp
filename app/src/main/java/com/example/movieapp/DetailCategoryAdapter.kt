package com.example.movieapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class DetailCategoryAdapter(
    private val onDetailCategoryClick: (String) -> Unit
) : ListAdapter<DetailCategory, DetailCategoryAdapter.DetailCategoryViewHolder>(ContactDiffUtils()) {

    //criando o viewholder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailCategoryViewHolder{

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return DetailCategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailCategoryAdapter.DetailCategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DetailCategoryViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val tvDetailCategory = view.findViewById<TextView>(R.id.tv_category)

        fun bind(detailCategory: DetailCategory){
            tvDetailCategory.text = detailCategory.name
            itemView.setOnClickListener{
                onDetailCategoryClick(detailCategory.name)
            }
        }
    }

    class ContactDiffUtils : DiffUtil.ItemCallback<DetailCategory>(){
        override fun areItemsTheSame(oldItem: DetailCategory, newItem: DetailCategory): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DetailCategory, newItem: DetailCategory): Boolean {
            return oldItem.name == newItem.name
        }
    }

}