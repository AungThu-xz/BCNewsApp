package com.aungthu.bcnewsapp.view.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aungthu.bcnewsapp.R
import com.aungthu.bcnewsapp.adapter.NewsAdapter
import com.aungthu.bcnewsapp.db.model.NewsModel
import com.aungthu.bcnewsapp.utils.Constants.NEWS_CONTENT
import com.aungthu.bcnewsapp.utils.Constants.NEWS_DESCRIPTION
import com.aungthu.bcnewsapp.utils.Constants.NEWS_IMAGE_URL
import com.aungthu.bcnewsapp.utils.Constants.NEWS_PUBLICATION_TIME
import com.aungthu.bcnewsapp.utils.Constants.NEWS_SOURCE
import com.aungthu.bcnewsapp.utils.Constants.NEWS_TITLE
import com.aungthu.bcnewsapp.utils.Constants.NEWS_URL
import com.aungthu.bcnewsapp.view.detail.NewsDetailActivity

class BusinessFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_business, container, false)
        val newsData: MutableList<NewsModel> = MainActivity.businessNews
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = NewsAdapter(newsData)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : NewsAdapter.OnItemClickListener {

            override fun onItemClick(position: Int) {
                val intent = Intent(context, NewsDetailActivity::class.java).apply {
                    putExtra(NEWS_URL, newsData[position].url)
                    putExtra(NEWS_TITLE, newsData[position].headLine)
                    putExtra(NEWS_IMAGE_URL, newsData[position].image)
                    putExtra(NEWS_DESCRIPTION, newsData[position].description)
                    putExtra(NEWS_SOURCE, newsData[position].source)
                    putExtra(NEWS_PUBLICATION_TIME, newsData[position].time)
                    putExtra(NEWS_CONTENT, newsData[position].content)
                }

                startActivity(intent)

            }
        })

        // Ignore
        adapter.setOnItemLongClickListener(object : NewsAdapter.OnItemLongClickListener {
            override fun onItemLongClick(position: Int) = Unit
        })

        return view
        // Inflate the layout for this fragment
    }

}