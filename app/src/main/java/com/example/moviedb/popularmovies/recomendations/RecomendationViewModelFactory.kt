package com.example.moviedb.popularmovies.recomendations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviedb.network.MovieRepository
import com.example.moviedb.popularmovies.casts.CastViewModel

class RecomendationViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RecomendationViewModel::class.java)) {
            RecomendationViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("RecomendationViewModel Not Found")
        }
    }

}