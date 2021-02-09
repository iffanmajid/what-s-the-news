package com.whatsthenews.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class NewsResponse(
    @field:Json(name = "status")
    val status: String?,
    @field:Json(name = "totalResults")
    val totalResults: Int?,
    @field:Json(name = "articles")
    val articles: List<Article>?
)

@JsonClass(generateAdapter = true)
data class Article(
    @field:Json(name = "source")
    val source: Source?,
    @field:Json(name = "author")
    val author: String?,
    @field:Json(name = "title")
    val title: String?,
    @field:Json(name = "description")
    val description: String?,
    @field:Json(name = "url")
    val url: String?,
    @field:Json(name = "urlToImage")
    val urlToImage: String?,
    @field:Json(name = "publishedAt")
    val publishedAt: String?,
    @field:Json(name = "content")
    val content: String?
)

@JsonClass(generateAdapter = true)
data class Source(
    @field:Json(name = "id")
    val id: Any?,
    @field:Json(name = "name")
    val name: String?
)