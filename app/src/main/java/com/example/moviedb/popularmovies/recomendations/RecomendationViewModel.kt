package com.example.moviedb.popularmovies.recomendations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedb.model.casts.Cast
import com.example.moviedb.model.recomendations.Recomendations
import com.example.moviedb.network.MovieRepository
import com.example.moviedb.utils.Contsants
import kotlinx.coroutines.*

class RecomendationViewModel(private val repository: MovieRepository) : ViewModel() {

    private val errorMessage = MutableLiveData<String>()
    private var recomendationsLivedata = MutableLiveData<List<Recomendations>>()
    var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    private val loading = MutableLiveData<Boolean>()


    //by movie id
    fun getRecomendations(id:Int){
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getAllRecomendations(id, Contsants.APIKEY)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    recomendationsLivedata.postValue(response.body()!!.results)
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }
    fun observeRecomendations(): LiveData<List<Recomendations>> {
        return  recomendationsLivedata
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