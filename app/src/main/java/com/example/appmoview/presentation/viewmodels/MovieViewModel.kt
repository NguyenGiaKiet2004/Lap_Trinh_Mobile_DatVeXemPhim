package com.example.appmoview.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.appmoview.data.repository.MovieRepositoryImpl
import com.example.appmoview.presentation.screens.Movie

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MovieRepositoryImpl(application.applicationContext)
    val movieList: LiveData<List<Movie>> = repository.movieList

    fun loadMovies() {
        repository.getMovies()
    }
}
