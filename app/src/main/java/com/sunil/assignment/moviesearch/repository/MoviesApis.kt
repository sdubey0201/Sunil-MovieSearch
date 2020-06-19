package com.sunil.assignment.moviesearch.repository

import com.sunil.assignment.moviesearch.model.MovieDetailResponse
import com.sunil.assignment.moviesearch.model.MovieSearchResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApis {
    @GET("/")
    fun getMovieList(
        @Query("apikey") apikey: String,
        @Query("s") s: String,
        @Query("type") type: String,
        @Query("page") page: Int
    ): Single<MovieSearchResponse>

    @GET("/")
    fun getMovieDetail(
        @Query("apikey") apikey: String,
        @Query("i") id: String
    ): Single<MovieDetailResponse>
}