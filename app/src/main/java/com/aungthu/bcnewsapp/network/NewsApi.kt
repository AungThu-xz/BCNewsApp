package com.aungthu.bcnewsapp.network

import com.aungthu.bcnewsapp.network.responseModel.NewsDataResponse
import com.aungthu.bcnewsapp.network.responseModel.SourceDataResponse
import com.aungthu.bcnewsapp.utils.Constants
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET(Constants.EVERYTHING_URL)
    fun getAllNews(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("apiKey") key: String
    ): Call<NewsDataResponse>

    @GET(Constants.TOP_HEADLINES_URL)
    fun getHeadLineNews(
        @Query("country") country: String,
        @Query("category") category: String?,
        @Query("page") page: Int,
        @Query("apiKey") key: String
    ): Call<NewsDataResponse>

    @GET(Constants.SOURCE_URL)
    suspend fun getSourcesNews(
        @Query("country") country: String = "us",
        @Query("apiKey") key: String,
    ): Response<SourceDataResponse>

}