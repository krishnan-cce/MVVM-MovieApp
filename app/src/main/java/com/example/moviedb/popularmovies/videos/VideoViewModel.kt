package com.example.moviedb.popularmovies.videos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedb.model.casts.Cast
import com.example.moviedb.model.videos.Videos
import com.example.moviedb.network.MovieRepository
import com.example.moviedb.utils.Contsants
import kotlinx.coroutines.*

class VideoViewModel(private val repository: MovieRepository) : ViewModel() {
    private val errorMessage = MutableLiveData<String>()
    private var videosLivedata = MutableLiveData<List<Videos>>()
    var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    private val loading = MutableLiveData<Boolean>()

    //videos by movie id
    fun getRecomendations(id:Int){
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getAllVideos(id, Contsants.APIKEY)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    videosLivedata.postValue(response.body()!!.results)
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    fun observevideos(): LiveData<List<Videos>> {
        return  videosLivedata
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}