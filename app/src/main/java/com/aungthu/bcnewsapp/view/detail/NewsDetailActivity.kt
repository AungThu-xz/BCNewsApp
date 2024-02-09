package com.aungthu.bcnewsapp.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.aungthu.bcnewsapp.R
import com.aungthu.bcnewsapp.db.model.NewsModel
import com.aungthu.bcnewsapp.repository.viewModel.NewsViewModel
import com.aungthu.bcnewsapp.utils.Constants.NEWS_CONTENT
import com.aungthu.bcnewsapp.utils.Constants.NEWS_DESCRIPTION
import com.aungthu.bcnewsapp.utils.Constants.NEWS_IMAGE_URL
import com.aungthu.bcnewsapp.utils.Constants.NEWS_PUBLICATION_TIME
import com.aungthu.bcnewsapp.utils.Constants.NEWS_SOURCE
import com.aungthu.bcnewsapp.utils.Constants.NEWS_TITLE
import com.aungthu.bcnewsapp.utils.Constants.NEWS_URL
import java.util.ArrayList

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var newsWebView: WebView
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsData: ArrayList<NewsModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        newsWebView = findViewById(R.id.news_webview)
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        //loading data into list
        newsData = ArrayList(1)
        val newsUrl = intent.getStringExtra(NEWS_URL)
        val newsContent =
            intent.getStringExtra(NEWS_CONTENT) + ". get paid version to hear full news. "
        newsData.add(
            NewsModel(
                intent.getStringExtra(NEWS_TITLE)!!,
                intent.getStringExtra(NEWS_IMAGE_URL),
                intent.getStringExtra(NEWS_DESCRIPTION),
                newsUrl,
                intent.getStringExtra(NEWS_SOURCE),
                intent.getStringExtra(NEWS_PUBLICATION_TIME),
                newsContent
            )
        )

        // Webview
        newsWebView.apply {
            settings.apply {
                domStorageEnabled = true
                loadsImagesAutomatically = true
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                javaScriptEnabled = true
            }
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
        }


        if (newsUrl != null) {
            newsWebView.loadUrl(newsUrl)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}