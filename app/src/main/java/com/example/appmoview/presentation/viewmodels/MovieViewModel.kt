package com.example.appmoview.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.appmoview.data.repository.MovieRepositoryImpl
import com.example.appmoview.domain.model.MovieRequest

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MovieRepositoryImpl(application.applicationContext)

    val movieList: LiveData<List<MovieRequest>> = repository.movies
    val isLoading: LiveData<Boolean> = repository.isLoading

    fun loadMovies() {
        repository.fetchAllMovies()
    }
}
