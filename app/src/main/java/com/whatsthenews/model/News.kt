package com.whatsthenews.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class News(
        @PrimaryKey
        val title: String,
        val description: String,
        val imageUrl: String,
        val newsUrl: String,
        val isFavorited: Boolean = false
)