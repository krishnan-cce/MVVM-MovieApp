package com.example.moviedb.popularmovies.webview

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.moviedb.R
import com.example.moviedb.databinding.ActivityCastBinding
import com.example.moviedb.databinding.ActivityWebBinding
import com.example.moviedb.model.detail.DetailResponse
import com.example.moviedb.model.movies.Result
import com.example.moviedb.popularmovies.details.PopularMovieDetailsActivity

class WebActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWebBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = this.getSharedPreferences("HOMEPAGE_POP", android.content.Context.MODE_PRIVATE)
        val det = pref.getString("homepage","None")
        if (det != null) {
            executeWebView(det)
        }
    }
    fun executeWebView(url:String){
        binding.popularWebview.apply {
            webViewClient = WebViewClient()
            if (url.isNotEmpty()){
                loadUrl(url)
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, PopularMovieDetailsActivity::class.java)
        startActivity(intent)
        finish()
    }

}