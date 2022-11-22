package com.example.moviedb.popularmovies.casts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedb.model.casts.Cast
import com.example.moviedb.model.person.PersonResponse
import com.example.moviedb.network.MovieRepository
import com.example.moviedb.utils.Contsants.APIKEY
import kotlinx.coroutines.*

class CastViewModel(private val repository: MovieRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    private var castMovieLivedata = MutableLiveData<List<Cast>>()
    //private var personDetailLivedata = MutableLiveData<List<PersonResponse>>()
    private var personDetailLivedata = MutableLiveData<PersonResponse>()
    var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    //by cast id
    fun getCast(id:Int){
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getPersonById(id, APIKEY)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    personDetailLivedata.postValue(response.body())
                    loading.value = false
                }else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }


    fun observePerson(): LiveData<PersonResponse>{

        return personDetailLivedata
    }
    //by movie id
    fun getAllMovieCasts(id:Int) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getALlMovieCast(id,APIKEY)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    castMovieLivedata.postValue(response.body()!!.cast)
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }

    fun observeCasts(): LiveData<List<Cast>> {
        return  castMovieLivedata
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