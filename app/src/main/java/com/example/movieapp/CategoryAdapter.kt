package com.example.movieapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

//O adapter vai adaptar as categorias da lista criadas na MainActivity dentro do item_category.xml
class CategoryAdapter:
    ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(ContactDiffUtils()) {

        //cria o viewholder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        // alimenta (infla) o item da lista com o resultado do CategoryViewHolder
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }
        // bind - atrelar o dado com a UI (views)
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // O ViewHolder vai segurar os dados (ex: nome) e inflar os itens dentro da lista
    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view){
        // Recuperando os dados do XML
        private val tvCategory = view.findViewById<TextView>(R.id.tv_category)

        //função está sendo criada para ser utilizada na função onBindViewHolder
        fun bind(category: Category){
            tvCategory.text = category.name

        }
    }
    // O que é o DiffUtils: compara a diferença de um item para o outro, quando a lista mudar
    class ContactDiffUtils : DiffUtil.ItemCallback<Category>(){
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.name == newItem.name
        }

    }


}

