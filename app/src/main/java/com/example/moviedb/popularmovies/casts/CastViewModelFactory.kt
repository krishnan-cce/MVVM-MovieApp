package com.example.moviedb.popularmovies.casts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviedb.network.MovieRepository

class CastViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CastViewModel::class.java)) {
            CastViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("CastViewModel Not Found")
        }
    }



}