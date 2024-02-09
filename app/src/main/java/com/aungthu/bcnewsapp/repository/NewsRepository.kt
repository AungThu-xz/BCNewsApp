package com.aungthu.bcnewsapp.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aungthu.bcnewsapp.db.NewsAppDatabase
import com.aungthu.bcnewsapp.db.model.NewsModel
import com.aungthu.bcnewsapp.network.NewsApi
import com.aungthu.bcnewsapp.network.RetrofitHelper
import com.aungthu.bcnewsapp.network.responseModel.NewsDataResponse
import com.aungthu.bcnewsapp.utils.Constants.API_KEY
import com.aungthu.bcnewsapp.view.home.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository {
    companion object {

        private var newsAppDatabase: NewsAppDatabase? = null

        private fun initializeDB(context: Context): NewsAppDatabase {
            return NewsAppDatabase.getDatabaseClient(context)
        }

        fun insertNews(context: Context, news: NewsModel) {

            newsAppDatabase = initializeDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                newsAppDatabase!!.newsDao().insertNews(news)
            }
        }

        fun deleteNews(context: Context, news: NewsModel) {

            newsAppDatabase = initializeDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                newsAppDatabase!!.newsDao().deleteNews(news)
            }
        }

        fun getAllNews(context: Context): LiveData<List<NewsModel>> {

            newsAppDatabase = initializeDB(context)
            return newsAppDatabase!!.newsDao().getNewsFromDatabase()
        }

    }


    fun getDailyNewsApiCall(q: String?): MutableLiveData<List<NewsModel>> {
        val newsList = MutableLiveData<List<NewsModel>>()

        q?.let { it ->
            RetrofitHelper.getInstance().create(NewsApi::class.java)
                .getAllNews(it, 1, API_KEY)
                .enqueue(object : Callback<NewsDataResponse> {
                    override fun onResponse(
                        call: Call<NewsDataResponse>,
                        response: Response<NewsDataResponse>
                    ) {
                        if (response.isSuccessful) {

                            val body = response.body()

                            if (body != null) {
                                val tempNewsList = mutableListOf<NewsModel>()

                                body.articles.forEach {
                                    tempNewsList.add(
                                        NewsModel(
                                            it.title,
                                            it.urlToImage,
                                            it.description,
                                            it.url,
                                            it.source.name,
                                            it.publishedAt,
                                            it.content
                                        )
                                    )
                                }
                                newsList.value = tempNewsList
                            }

                        } else {

                            val jsonObj: JSONObject?

                            try {
                                jsonObj = response.errorBody()?.string()?.let { JSONObject(it) }
                                if (jsonObj != null) {
                                    MainActivity.apiRequestError = true
                                    MainActivity.errorMessage = jsonObj.getString("message")
                                    val tempNewsList = mutableListOf<NewsModel>()
                                    newsList.value = tempNewsList
                                }
                            } catch (e: JSONException) {
                                Log.d("JSONException", "" + e.message)
                            }

                        }
                    }

                    override fun onFailure(call: Call<NewsDataResponse>, t: Throwable) {
                        MainActivity.apiRequestError = true
                        MainActivity.errorMessage = t.localizedMessage as String
                        Log.d("err_msg", "msg" + t.localizedMessage)
                    }

                })
        }
        return newsList
    }

    // get news from API
    fun getNewsHeadlineApiCall(category: String?): MutableLiveData<List<NewsModel>> {

        val newsList = MutableLiveData<List<NewsModel>>()

        val call = RetrofitHelper.getInstance().create(NewsApi::class.java)
            .getHeadLineNews("m", category, 1, API_KEY) //put your api key here

        call.enqueue(object : Callback<NewsDataResponse> {
            override fun onResponse(
                call: Call<NewsDataResponse>,
                response: Response<NewsDataResponse>
            ) {

                if (response.isSuccessful) {

                    val body = response.body()

                    if (body != null) {
                        val tempNewsList = mutableListOf<NewsModel>()

                        body.articles.forEach {
                            tempNewsList.add(
                                NewsModel(
                                    it.title,
                                    it.urlToImage,
                                    it.description,
                                    it.url,
                                    it.source.name,
                                    it.publishedAt,
                                    it.content
                                )
                            )
                        }
                        newsList.value = tempNewsList
                    }

                } else {

                    val jsonObj: JSONObject?

                    try {
                        jsonObj = response.errorBody()?.string()?.let { JSONObject(it) }
                        if (jsonObj != null) {
                            MainActivity.apiRequestError = true
                            MainActivity.errorMessage = jsonObj.getString("message")
                            val tempNewsList = mutableListOf<NewsModel>()
                            newsList.value = tempNewsList
                        }
                    } catch (e: JSONException) {
                        Log.d("JSONException", "" + e.message)
                    }

                }
            }

            override fun onFailure(call: Call<NewsDataResponse>, t: Throwable) {

                MainActivity.apiRequestError = true
                MainActivity.errorMessage = t.localizedMessage as String
                Log.d("err_msg", "msg" + t.localizedMessage)
            }
        })

        return newsList
    }

}