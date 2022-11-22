package com.example.moviedb.popularmovies.casts.fragment


import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.moviedb.databinding.TabLayoutBinding
import com.example.moviedb.popularmovies.MovieActivity
import com.google.android.material.tabs.TabLayoutMediator


class TabActivity : AppCompatActivity() {
    //private lateinit var binding : TabLayoutBinding

    private val arraylist = arrayOf(
        "Cast",
        "Crew"

    )


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding: TabLayoutBinding = TabLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout


        val adapter = TabAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = arraylist[position]
        }.attach()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBackPressed() {
        val intent = Intent(this, MovieActivity::class.java)
        startActivity(intent)
        finish()
    }




}
