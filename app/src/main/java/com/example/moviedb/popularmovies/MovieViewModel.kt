package com.example.moviedb.popularmovies

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.moviedb.model.movies.Result
import com.example.moviedb.model.casts.Cast
import com.example.moviedb.model.detail.DetailResponse
import com.example.moviedb.model.detail.Genre
import com.example.moviedb.model.detail.ProductionCompany
import com.example.moviedb.model.movies.Movies
import com.example.moviedb.model.recomendations.Recomendations
import com.example.moviedb.model.videos.Videos
import com.example.moviedb.network.RetrofitClient

import com.example.moviedb.utils.Contsants
import com.example.moviedb.utils.Contsants.APIKEY
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

@RequiresApi(Build.VERSION_CODES.M)
class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private var genreLiveData = MutableLiveData<List<Genre>>()
    private var productionCompanyLiveData = MutableLiveData<List<ProductionCompany>>()
    private var movieLiveDatausingCoroutines = MutableLiveData<List<Result>>()


    private var detailMovies = MutableLiveData<DetailResponse>()

    fun getPopularMovieDetails(id : Int){
        viewModelScope.launch {
            val request = RetrofitClient.apiInterface.getMovieDetails(id, Contsants.APIKEY)
            if (request.isSuccessful){
                detailMovies.postValue(request.body())
                genreLiveData.postValue(request.body()!!.genres) //value = request.body()!!.genres
                productionCompanyLiveData.postValue(request.body()!!.productionCompanies)
            }
        }
    }
    fun getAllGenre(): LiveData<List<Genre>>{
        return genreLiveData
    }



    fun observePopularMovieDetails(): MutableLiveData<DetailResponse> {
        return  detailMovies
    }


    fun getMovies(){
        viewModelScope.launch {
            val request = RetrofitClient.apiInterface.getMovies(APIKEY)
            if (request.isSuccessful){
                movieLiveDatausingCoroutines.value = request.body()!!.results
            }
        }
    }

    fun observePopularMovieLiveData(): LiveData<List<Result>>{
        return  movieLiveDatausingCoroutines
    }



    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }







    //Without Using Coroutines

//    fun getPopularMovies() {
//        RetrofitClient.apiInterface.getPopularMovies(APIKEY).enqueue(object :
//            Callback<Movies> {
//            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
//                if (response.body()!=null){
//                    movieLiveData.value = response.body()!!.results
//                }
//                else{
//                    return
//                }
//            }
//            override fun onFailure(call: Call<Movies>, t: Throwable) {
//                Log.d("TAG",t.message.toString())
//            }
//        })
//    }
//
//    fun observeMovieLiveData() : LiveData<List<Result>> {
//        return movieLiveData
//    }
}
