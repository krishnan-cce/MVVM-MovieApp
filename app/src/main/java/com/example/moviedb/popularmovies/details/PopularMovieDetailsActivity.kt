package com.example.moviedb.popularmovies.details

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.moviedb.databinding.DetailsResponseLayoutBinding
import com.example.moviedb.model.casts.Cast
import com.example.moviedb.model.recomendations.Recomendations
import com.example.moviedb.model.videos.Videos
import com.example.moviedb.network.MovieRepository
import com.example.moviedb.network.RetrofitClient
import com.example.moviedb.popularmovies.MovieActivity
import com.example.moviedb.popularmovies.MovieViewModel
import com.example.moviedb.popularmovies.casts.CastActivity
import com.example.moviedb.popularmovies.casts.CastAdapter
import com.example.moviedb.popularmovies.casts.CastViewModel
import com.example.moviedb.popularmovies.casts.CastViewModelFactory
import com.example.moviedb.popularmovies.casts.fragment.TabActivity
import com.example.moviedb.popularmovies.recomendations.RecomendationViewModel
import com.example.moviedb.popularmovies.recomendations.RecomendationViewModelFactory
import com.example.moviedb.popularmovies.recomendations.RecomendedAdapter
import com.example.moviedb.popularmovies.similar.SimilarAdapter
import com.example.moviedb.popularmovies.similar.SimilarViewModel
import com.example.moviedb.popularmovies.similar.SimilarViewModelFactory
import com.example.moviedb.popularmovies.videos.VideoAdapter
import com.example.moviedb.popularmovies.videos.VideoViewModel
import com.example.moviedb.popularmovies.videos.VideoViewModelFactory
import com.example.moviedb.popularmovies.webview.WebActivity

import java.text.NumberFormat
import java.util.*

@SuppressLint("SetTextI18n")
@RequiresApi(Build.VERSION_CODES.M)
class PopularMovieDetailsActivity : AppCompatActivity() , SimilarAdapter.OnItemClickListener,RecomendedAdapter.OnItemClickListener
,VideoAdapter.OnItemClickListener,CastAdapter.OnItemClickListener
{
    private lateinit var binding : DetailsResponseLayoutBinding
    private lateinit var viewModel: MovieViewModel
    private lateinit var castViewModel: CastViewModel
    private lateinit var recomendationsViewModel: RecomendationViewModel
    private lateinit var similarViewModel: SimilarViewModel
    private lateinit var videoViewModel: VideoViewModel
    private lateinit var genreAdapter : GenreAdapter
    private lateinit var castAdapter : CastAdapter
    private lateinit var recomendedAdapter : RecomendedAdapter
    private lateinit var videoAdapter : VideoAdapter
    private lateinit var similarAdapter : SimilarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailsResponseLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)




        val retrofitService = RetrofitClient.apiInterface
        val mainRepository = MovieRepository(retrofitService)
        similarViewModel = ViewModelProvider(this, SimilarViewModelFactory(mainRepository))[SimilarViewModel::class.java]
        videoViewModel = ViewModelProvider(this, VideoViewModelFactory(mainRepository))[VideoViewModel::class.java]
        castViewModel = ViewModelProvider(this, CastViewModelFactory(mainRepository))[CastViewModel::class.java]
        recomendationsViewModel =  ViewModelProvider(this, RecomendationViewModelFactory(mainRepository))[RecomendationViewModel::class.java]
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        setupGenre()
        setupCasts()
        setupRecomendations()
        setupVideos()
        setupSimilar()

        val pref = this.getSharedPreferences("PREFS", android.content.Context.MODE_PRIVATE)
        val det = pref.getInt("movieId",0)
        //val details : Result = this.intent.getParcelableExtra<Result>("movieDetails")!!

            viewModel.getPopularMovieDetails(det)
            castViewModel.getAllMovieCasts(det)
            recomendationsViewModel.getRecomendations(det)
            videoViewModel.getRecomendations(det)
            similarViewModel.getSimilarMovies(det)



        viewModel.observePopularMovieDetails().observe(this,Observer{movieList ->
           binding.tvPopularity.text =  "("+movieList.voteCount.toString() +")"
            binding.ratingBar.rating = (movieList.voteAverage).toFloat() / 2
            binding.tvOriginalTitle.text = movieList.title
            binding.tvTitlebar.text = movieList.title
            Glide.with(this).load("https://image.tmdb.org/t/p/w500" + movieList.posterPath).into(binding.ivPosterPath)
            Glide.with(this).load("https://image.tmdb.org/t/p/w500" + movieList.backdropPath).into(binding.ivBackdropPath)
            binding.tvOverview.text = movieList.overview
            binding.tvRating.text = movieList.voteAverage.toString()

            binding.tvReleaseDate.text = movieList.releaseDate
            binding.tvLanguage.text = movieList.originalLanguage


            val format = NumberFormat.getCurrencyInstance(Locale.US)
            val budget: String = format.format(movieList.budget)
            val revenue: String = format.format(movieList.revenue)
            binding.tvBudget.text = budget
            binding.tvRevenue.text = revenue

            binding.ivWeb.setOnClickListener {
                val intent = Intent(this, WebActivity::class.java)
                val editor = this.getSharedPreferences("HOMEPAGE_POP", Context.MODE_PRIVATE).edit()
                editor.putString("homepage", movieList.homepage)
                editor.apply()
                startActivity(intent)
                finish()
            }


       })


        viewModel.getAllGenre().observe(this,Observer{genreList ->
            genreAdapter.setData(genreList)
        })

        castViewModel.observeCasts().observe(this,Observer{castList ->
            castAdapter.setData(castList)
        })

        recomendationsViewModel.observeRecomendations().observe(this,Observer{recomendedList ->
            if (recomendedList.isEmpty()){
                binding.recomended.visibility = View.GONE
                binding.rvRecomendations.visibility = View.GONE
                binding.recomendedSeeAll.visibility = View.GONE
            }else{
                recomendedAdapter.setData(recomendedList)
            }
        })

        videoViewModel.observevideos().observe(this,Observer{videoList ->
            if (videoList.isEmpty()){
                binding.rvVideos.visibility = View.GONE
                binding.videos.visibility = View.GONE
                binding.textVideosee.visibility = View.GONE
            }else{
                videoAdapter.setData(videoList)
            }

        })

        similarViewModel.observeSimilarMovies().observe(this,Observer{similarList ->
            if (similarList.isEmpty()){
                binding.similar.visibility = View.GONE
                binding.rvSimilar.visibility = View.GONE
                binding.textsimilar.visibility = View.GONE
            }else{
                similarAdapter.setData(similarList)
            }
        })
    }

    override fun onBackPressed() {
        val intent = Intent(this, MovieActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun setupGenre(){
        genreAdapter = GenreAdapter()
        binding.rvGenre.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = genreAdapter
        }
    }
    fun setupCasts(){
        castAdapter = CastAdapter(this)
        binding.rvCasts.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = castAdapter
        }
    }
    fun setupRecomendations(){

        recomendedAdapter = RecomendedAdapter(this)
        binding.rvRecomendations.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = recomendedAdapter

        }
    }
    fun setupVideos(){

        videoAdapter = VideoAdapter(this)
        binding.rvVideos.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = videoAdapter

        }
    }
    fun setupSimilar(){

        similarAdapter = SimilarAdapter(this,this)
        binding.rvSimilar.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = similarAdapter

        }
    }

    override fun onItemEditCLick(similarMovies: com.example.moviedb.model.similar.Result) {
        val intent = Intent(this, PopularMovieDetailsActivity::class.java)
        val editor = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
        editor.remove("PREFS")
        editor.putInt("movieId", similarMovies.id)
        editor.apply()
        startActivity(intent)
        finish()
    }

    override fun onItemRecomendedClick(recomendedMovies: Recomendations) {
        val intent = Intent(this, PopularMovieDetailsActivity::class.java)
        val editor = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
        editor.remove("PREFS")
        editor.putInt("movieId", recomendedMovies.id)
        editor.apply()
        startActivity(intent)
        finish()
    }

    override fun onItemVideoCLick(videos: Videos) {

           val urlStr = "https://www.youtube.com/watch?v=" + videos.key
           val intent = Intent(Intent.ACTION_VIEW)
           intent.data = Uri.parse(urlStr)
           startActivity(intent)

    }

    override fun onItemTabCLick(casts: Cast) {
//        Toast.makeText(this,casts.id.toString(),Toast.LENGTH_LONG).show()
        val intent = Intent(this, CastActivity::class.java)
        val editor = this.getSharedPreferences("CAST_PREFS", Context.MODE_PRIVATE).edit()
        editor.putInt("castId", casts.id)
        editor.apply()
        startActivity(intent)
        finish()
    }


}