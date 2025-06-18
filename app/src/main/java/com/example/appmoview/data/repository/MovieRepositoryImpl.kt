package com.example.appmoview.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appmoview.data.source.retrofit.RetrofitClient
import com.example.appmoview.domain.model.ApiResponse
import com.example.appmoview.domain.model.MovieDetail
import com.example.appmoview.domain.model.MovieRequest
import com.example.appmoview.domain.model.Showtime
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

    override fun fetchMovieById(id: Int, onResult: (MovieDetail?) -> Unit) {
        _isLoading.value = true

        RetrofitClient.movieInstance.getMovieById(id).enqueue(object : Callback<ApiResponse<MovieDetail>> {
            override fun onResponse(call: Call<ApiResponse<MovieDetail>>, response: Response<ApiResponse<MovieDetail>>) {
                _isLoading.value = false
                if (response.isSuccessful && response.body()?.success == true) {
                    onResult(response.body()?.data)
                } else {
                    _errorMessage.value = "Không thể tải phim (${response.code()})"
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<ApiResponse<MovieDetail>>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Lỗi kết nối: ${t.localizedMessage}"
                onResult(null)
            }
        })
    }

    private val _showtimeList = MutableLiveData<List<Showtime>>()
    override val showtimeList: LiveData<List<Showtime>> get() = _showtimeList

    override fun fetchAllShowtime() {
        _isLoading.value = true

        RetrofitClient.movieInstance.getAllShowtime().enqueue(object : Callback<List<Showtime>> {
            override fun onResponse(call: Call<List<Showtime>>, response: Response<List<Showtime>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _showtimeList.value = response.body() ?: emptyList()
                } else {
                    _errorMessage.value = "Không thể tải lịch chiếu (${response.code()})"
                }
            }

            override fun onFailure(call: Call<List<Showtime>>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Lỗi kết nối: ${t.localizedMessage}"
            }
        })
    }

    override fun checkBookedSeats(
        showtimeId: Int,
        seatIds: List<Int>,
        onResult: (List<Int>?) -> Unit
    ) {
        _isLoading.value = true

        RetrofitClient.movieInstance.checkBookedSeats(showtimeId, seatIds)
            .enqueue(object : Callback<List<Int>> {
                override fun onResponse(call: Call<List<Int>>, response: Response<List<Int>>) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        onResult(response.body())
                    } else {
                        _errorMessage.value = "Không thể kiểm tra ghế đã đặt (${response.code()})"
                        onResult(null)
                    }
                }

                override fun onFailure(call: Call<List<Int>>, t: Throwable) {
                    _isLoading.value = false
                    _errorMessage.value = "Lỗi kết nối: ${t.localizedMessage}"
                    onResult(null)
                }
            })
    }



}
