package com.aungthu.bcnewsapp.network.responseModel

data class Article(
    val author: String,
    val description: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)