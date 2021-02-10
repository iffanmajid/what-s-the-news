package com.whatsthenews.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.whatsthenews.R
import com.whatsthenews.databinding.ItemNewsBinding
import com.whatsthenews.model.News

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    
    private val items: MutableList<News> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemNewsBinding>(inflater, R.layout.item_news, parent, false)
        return NewsViewHolder(binding).apply { 
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?: return@setOnClickListener
                
            }
        }
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.apply {
            news = items[position]
            executePendingBindings()
        }

    }

    override fun getItemCount() = items.size

    fun setNewsList(newsList: List<News>) {
        val previousItemSize = items.size
        items.clear()
        items.addAll(newsList)
        notifyItemRangeChanged(previousItemSize, newsList.size)
    }

    class NewsViewHolder(val binding: ItemNewsBinding) :
    RecyclerView.ViewHolder(binding.root)

}