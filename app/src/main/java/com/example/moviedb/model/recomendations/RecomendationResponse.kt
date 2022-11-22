package com.example.moviedb.model.recomendations


import com.google.gson.annotations.SerializedName

data class RecomendationResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Recomendations>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)