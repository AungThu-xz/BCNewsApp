package com.aungthu.bcnewsapp.repository.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aungthu.bcnewsapp.db.model.NewsModel
import com.aungthu.bcnewsapp.repository.NewsRepository

class NewsViewModel : ViewModel() {
    private var newsViewModel: MutableLiveData<List<NewsModel>>? = null

    var newsData: LiveData<List<NewsModel>>? = null
    fun getAllNews(q: String): MutableLiveData<List<NewsModel>>? {
        newsViewModel = q.let { NewsRepository().getDailyNewsApiCall(it) }
        return newsViewModel
    }

    fun getNewsHeadLine(category: String): MutableLiveData<List<NewsModel>>? {
        newsViewModel = category.let { NewsRepository().getNewsHeadlineApiCall(it) }
        return newsViewModel
    }


    fun getNewsFromDB(context: Context): LiveData<List<NewsModel>>? {
        newsData = NewsRepository.getAllNews(context)
        return newsData
    }
}