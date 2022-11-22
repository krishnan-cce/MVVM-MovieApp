package com.example.moviedb.network

import com.example.moviedb.model.movies.Movies
import com.example.moviedb.model.casts.CastResponse
import com.example.moviedb.model.detail.DetailResponse
import com.example.moviedb.model.person.PersonResponse
import com.example.moviedb.model.recomendations.RecomendationResponse
import com.example.moviedb.model.similar.SimilarResponse
import com.example.moviedb.model.videos.VideoResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {


        @GET("movie/popular?")
        fun getPopularMovies(@Query("api_key") api_key : String) : Call<Movies>


        @GET("movie/popular?")
        suspend fun getMovies(@Query("api_key") api_key : String) : Response<Movies>


        @GET("movie/{movie_id}?")
        suspend fun getMovieDetails(@Path("movie_id") id : Int, @Query("api_key") api_key : String) : Response<DetailResponse>

        @GET("movie/{movie_id}/credits?")
        suspend fun getMovieCasts(@Path("movie_id") id : Int, @Query("api_key") api_key : String) : Response<CastResponse>

        @GET("movie/{movie_id}/videos?")
        suspend fun getMovieVideos(@Path("movie_id") id : Int, @Query("api_key") api_key : String) : Response<VideoResponse>

        @GET("movie/{movie_id}/recommendations?")
        suspend fun getMovieRecomendations(@Path("movie_id") id : Int, @Query("api_key") api_key : String) : Response<RecomendationResponse>

        @GET("movie/{movie_id}/similar?")
        suspend fun getSimilarMovie(@Path("movie_id") id : Int, @Query("api_key") api_key : String) : Response<SimilarResponse>


        @GET("person/{person_id}?")
        suspend fun getPersonDetails(@Path("person_id") id : Int, @Query("api_key") api_key : String) : Response<PersonResponse>
}