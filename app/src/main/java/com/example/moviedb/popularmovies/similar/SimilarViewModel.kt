package com.example.moviedb.popularmovies.similar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedb.model.similar.Result
import com.example.moviedb.network.MovieRepository
import com.example.moviedb.utils.Contsants
import kotlinx.coroutines.*

class SimilarViewModel(private val repository: MovieRepository) : ViewModel() {

    private val errorMessage = MutableLiveData<String>()
    private var similarLivedata = MutableLiveData<List<Result>>()
    var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    private val loading = MutableLiveData<Boolean>()

    //similar by movie id
    fun getSimilarMovies(id:Int){
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getAllSimilarMovies(id, Contsants.APIKEY)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    similarLivedata.postValue(response.body()!!.results)
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    fun observeSimilarMovies(): LiveData<List<Result>> {
        return  similarLivedata
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