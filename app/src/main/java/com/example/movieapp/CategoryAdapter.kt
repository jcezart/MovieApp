package com.example.movieapp

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

//O adapter vai adaptar as categorias da lista criadas na MainActivity dentro do item_category.xml
class CategoryAdapter:
    ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(ContactDiffUtils()) {

        //cria o viewholder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        TODO("Not yet implemented")
    }
        // bind - atrelar o dado com a UI (views)
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    // O ViewHolder vai segurar os dados (ex: nome) e inflar os itens dentro da lista
    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view){

        //função está sendo criada para ser utilizada na função onBindViewHolder
        fun bind(){

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

