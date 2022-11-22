package com.example.moviedb.popularmovies

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviedb.databinding.ActivityMovieBinding
import com.example.moviedb.model.movies.Result

import com.example.moviedb.popularmovies.details.PopularMovieDetailsActivity

@RequiresApi(Build.VERSION_CODES.M)
class MovieActivity : AppCompatActivity(), MovieAdapter.OnItemClickListener {
    private lateinit var binding : ActivityMovieBinding
    private lateinit var viewModel: MovieViewModel
    private lateinit var movieAdapter : MovieAdapter


    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        viewModel.getMovies()


        viewModel.observePopularMovieLiveData().observe(this,Observer{movieList ->
            movieAdapter.setData(movieList)
        })



    }

    private fun prepareRecyclerView() {
        movieAdapter = MovieAdapter(this)
        binding.rvMovies.apply {
            layoutManager = GridLayoutManager(applicationContext,2)
            adapter = movieAdapter
        }
    }


    override fun onItemEditCLick(movies: Result) {
        //Toast.makeText(this, movies.title, Toast.LENGTH_SHORT).show()
//        val mFragmentManager = supportFragmentManager
//        val mFragmentTransaction = mFragmentManager.beginTransaction()
//        val mFragment = DetailsFragment()
//        val mBundle = Bundle()
//
//        mBundle.putString("P_MovieId", movies.id.toString())
//        mBundle.putString("P_MovieOverview", movies.overview)
//        mBundle.putString("P_MovieTitle", movies.title)
//        mBundle.putString("P_MoviePosterPath", movies.poster_path)
//        mBundle.putString("P_MovieBackground", movies.backdrop_path)
//        mBundle.putString("P_MovieDate", movies.release_date)
//        mBundle.putString("P_MoviePopularity", movies.popularity.toString())
//        mFragment.arguments = mBundle
//        mFragmentTransaction.add(R.id.constraint, mFragment).commit()

        val intent = Intent(this, PopularMovieDetailsActivity::class.java)
        //intent.putExtra("movieDetails",movies.id)

        val editor = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()

        editor.putInt("movieId", movies.id)
        editor.apply()
        startActivity(intent)
        finish()

    }




}
