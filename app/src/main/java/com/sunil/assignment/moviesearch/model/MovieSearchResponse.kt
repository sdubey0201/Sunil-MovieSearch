package com.sunil.assignment.moviesearch.model

import com.google.gson.annotations.SerializedName

data class MovieSearchResponse(
    @SerializedName("Search") val movies: List<Movie>,
    @SerializedName("totalResults") val total: Int,
    @SerializedName("Response") val status: Boolean
)

data class Movie(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: Int,
    @SerializedName("imdbID") val imdbID: String,
    @SerializedName("Type") val type: String,
    @SerializedName("Poster") val poster: String
) {

}
