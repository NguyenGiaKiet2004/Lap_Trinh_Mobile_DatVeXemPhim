package com.example.appmoview.domain.serviceInterface

import androidx.lifecycle.LiveData
import com.example.appmoview.domain.model.MovieRequest

interface MovieRepository {
    fun fetchAllMovies()
    fun fetchMovieById(id: Int, onResult: (MovieRequest?) -> Unit)
    val movies: LiveData<List<MovieRequest>>
    val isLoading: LiveData<Boolean>
    val errorMessage: LiveData<String>
}
