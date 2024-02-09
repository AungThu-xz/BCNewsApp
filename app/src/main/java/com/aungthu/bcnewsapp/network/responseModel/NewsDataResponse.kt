package com.aungthu.bcnewsapp.network.responseModel

data class NewsDataResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)