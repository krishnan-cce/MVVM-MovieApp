package com.example.moviedb.popularmovies.similar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviedb.network.MovieRepository



class SimilarViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SimilarViewModel::class.java)) {
            SimilarViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("SimilarViewModel Not Found")
        }
    }

}