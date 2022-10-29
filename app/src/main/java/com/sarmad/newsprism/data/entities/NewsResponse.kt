package com.sarmad.newsprism.data.entities

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)