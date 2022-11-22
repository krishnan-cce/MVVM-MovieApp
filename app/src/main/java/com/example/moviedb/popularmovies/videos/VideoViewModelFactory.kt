package com.example.moviedb.popularmovies.videos


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviedb.network.MovieRepository
import com.example.moviedb.popularmovies.casts.CastViewModel

class VideoViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(VideoViewModel::class.java)) {
            VideoViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("VideoViewModel Not Found")
        }
    }
}