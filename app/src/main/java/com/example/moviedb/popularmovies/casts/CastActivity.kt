package com.example.moviedb.popularmovies.casts

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.moviedb.R
import com.example.moviedb.databinding.ActivityCastBinding
import com.example.moviedb.databinding.ActivityMovieBinding
import com.example.moviedb.network.MovieRepository
import com.example.moviedb.network.RetrofitClient
import com.example.moviedb.popularmovies.MovieActivity
import com.example.moviedb.popularmovies.details.PopularMovieDetailsActivity
import com.example.moviedb.popularmovies.similar.SimilarViewModel
import com.example.moviedb.popularmovies.similar.SimilarViewModelFactory

class CastActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCastBinding
    private lateinit var personViewModel: CastViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofitService = RetrofitClient.apiInterface
        val mainRepository = MovieRepository(retrofitService)
        personViewModel = ViewModelProvider(this, CastViewModelFactory(mainRepository))[CastViewModel::class.java]

        val pref = this.getSharedPreferences("CAST_PREFS", android.content.Context.MODE_PRIVATE)
        val det = pref.getInt("castId",0)

        personViewModel.getCast(det)


        personViewModel.observePerson().observe(this, Observer{ personList ->

            Glide.with(this).load("https://image.tmdb.org/t/p/w500" + personList.profilePath).into(binding.ivPerson)
            binding.tvNameCast.text = personList.name

            if (personList.biography.isNotEmpty()){binding.tvBioCast.text = personList.biography}
            else{binding.tvBioCast.visibility = View.GONE
                binding.biography.visibility = View.GONE }

            binding.tvDobCast.text = personList.birthday
            binding.tvDobCast.text = personList.birthday
            binding.tvBirthplaceCast.text = personList.placeOfBirth
            binding.tvKnownforCast.text = personList.knownForDepartment
        })


        personViewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        personViewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBackPressed() {
        val intent = Intent(this, PopularMovieDetailsActivity::class.java)
        startActivity(intent)
        finish()
    }


}