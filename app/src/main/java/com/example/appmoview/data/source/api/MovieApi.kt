package com.example.appmoview.data.source.api


import com.example.appmoview.domain.model.MovieRequest
import com.example.appmoview.presentation.screens.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("movie/get")
    fun getAllMovies(): Call<List<MovieRequest>>

    @GET("movie/get/{movie_id}")
    suspend fun getMovieById(@Path("movie_id") movieId: Int): Movie
}
