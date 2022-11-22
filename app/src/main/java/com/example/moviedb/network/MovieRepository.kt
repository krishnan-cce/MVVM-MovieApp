package com.example.moviedb.network

class MovieRepository (private val apiInterface : ApiInterface) {

    suspend fun getALlMovies(apiKey : String){
        apiInterface.getMovies(apiKey)
    }

    suspend fun getALlMovieCast(id:Int ,apiKey : String) = apiInterface.getMovieCasts(id,apiKey)

    suspend fun getAllRecomendations(id:Int,apiKey: String) = apiInterface.getMovieRecomendations(id,apiKey)

    suspend fun getAllVideos(id:Int,apiKey: String) = apiInterface.getMovieVideos(id,apiKey)

    suspend fun getAllSimilarMovies(id:Int,apiKey: String) = apiInterface.getSimilarMovie(id,apiKey)

    suspend fun getPersonById(id:Int,apiKey: String) = apiInterface.getPersonDetails(id,apiKey)

}