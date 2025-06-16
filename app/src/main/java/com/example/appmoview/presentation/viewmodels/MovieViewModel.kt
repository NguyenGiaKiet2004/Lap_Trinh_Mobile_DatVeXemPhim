package com.example.appmoview.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appmoview.data.repository.MovieRepositoryImpl
import com.example.appmoview.domain.model.MovieDetail
import com.example.appmoview.domain.model.MovieRequest
import com.example.appmoview.domain.model.Showtime

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MovieRepositoryImpl(application.applicationContext)

    val movieList: LiveData<List<MovieRequest>> = repository.movies
    val showtimeList: LiveData<List<Showtime>> = repository.showtimeList
    val isLoading: LiveData<Boolean> = repository.isLoading

    // Thêm LiveData mới cho chi tiết phim
    private val _movieDetail = MutableLiveData<MovieDetail>()
    val movieDetail: LiveData<MovieDetail> get() = _movieDetail

    fun loadMovies() {
        repository.fetchAllMovies()
    }

    fun loadMovieDetail(id: Int) {
        repository.fetchMovieById(id) { detail ->
            detail?.let {
                _movieDetail.value = it
            }
        }
    }

    fun getShowTimes(){
        repository.fetchAllShowtime()
    }
}
