package com.example.appmoview.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appmoview.data.source.retrofit.RetrofitClient
import com.example.appmoview.domain.model.MovieRequest
import com.example.appmoview.domain.serviceInterface.MovieRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepositoryImpl(private val context: Context) : MovieRepository {

    private val _movies = MutableLiveData<List<MovieRequest>>()
    override val movies: LiveData<List<MovieRequest>> get() = _movies

    private val _isLoading = MutableLiveData<Boolean>()
    override val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    override val errorMessage: LiveData<String> get() = _errorMessage

    override fun fetchAllMovies() {
        _isLoading.value = true

        RetrofitClient.movieInstance.getAllMovies().enqueue(object : Callback<List<MovieRequest>> {
            override fun onResponse(call: Call<List<MovieRequest>>, response: Response<List<MovieRequest>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _movies.value = response.body() ?: emptyList()
                } else {
                    _errorMessage.value = "Không thể tải danh sách phim (${response.code()})"
                }
            }

            override fun onFailure(call: Call<List<MovieRequest>>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Lỗi kết nối: ${t.localizedMessage}"
            }
        })
    }
}
