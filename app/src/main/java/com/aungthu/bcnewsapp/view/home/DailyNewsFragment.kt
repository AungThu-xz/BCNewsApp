package com.aungthu.bcnewsapp.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aungthu.bcnewsapp.R
import com.aungthu.bcnewsapp.adapter.NewsAdapter
import com.aungthu.bcnewsapp.db.model.NewsModel
import com.aungthu.bcnewsapp.utils.Constants
import com.aungthu.bcnewsapp.utils.Constants.NEWS_CONTENT
import com.aungthu.bcnewsapp.utils.Constants.NEWS_DESCRIPTION
import com.aungthu.bcnewsapp.utils.Constants.NEWS_IMAGE_URL
import com.aungthu.bcnewsapp.utils.Constants.NEWS_PUBLICATION_TIME
import com.aungthu.bcnewsapp.utils.Constants.NEWS_SOURCE
import com.aungthu.bcnewsapp.utils.Constants.NEWS_TITLE
import com.aungthu.bcnewsapp.utils.Constants.NEWS_URL
import com.aungthu.bcnewsapp.view.detail.NewsDetailActivity

class DailyNewsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter
    private lateinit var dailyNews: List<NewsModel>
    private lateinit var newsDataForDown: List<NewsModel>
    var position = Constants.INITIAL_POSITION


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_daily_news, container, false)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        // Inflate the layout for this fragment
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager

        // Setting recyclerViews adapter
        dailyNews =
            MainActivity.dailyNews.slice(0 until Constants.TOP_HEADLINES_COUNT)
        newsDataForDown =
            MainActivity.dailyNews.slice(Constants.TOP_HEADLINES_COUNT until MainActivity.dailyNews.size - Constants.TOP_HEADLINES_COUNT)
        adapter = NewsAdapter(newsDataForDown)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : NewsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(context, NewsDetailActivity::class.java).apply {
                    putExtra(NEWS_URL, newsDataForDown[position].url)
                    putExtra(NEWS_TITLE, newsDataForDown[position].headLine)
                    putExtra(NEWS_IMAGE_URL, newsDataForDown[position].image)
                    putExtra(NEWS_DESCRIPTION, newsDataForDown[position].description)
                    putExtra(NEWS_SOURCE, newsDataForDown[position].source)
                    putExtra(NEWS_PUBLICATION_TIME, newsDataForDown[position].time)
                    putExtra(NEWS_CONTENT, newsDataForDown[position].content)
                }

                startActivity(intent)
            }
        })

        adapter.setOnItemLongClickListener(object : NewsAdapter.OnItemLongClickListener {
            override fun onItemLongClick(position: Int) = Unit
        })
        return view
    }
}