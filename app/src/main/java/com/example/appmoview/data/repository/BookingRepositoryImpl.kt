package com.example.appmoview.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appmoview.data.source.retrofit.RetrofitClient
import com.example.appmoview.domain.model.BookingHistory
import com.example.appmoview.domain.serviceInterface.BookingRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingRepositoryImpl(private val context: Context) : BookingRepository {

    val sharedPref = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    val userId = sharedPref.getString("user_id", null)?.toIntOrNull()


    private val _bookings = MutableLiveData<List<BookingHistory>>()
    override val bookings: LiveData<List<BookingHistory>> get() = _bookings

    private val _isLoading = MutableLiveData<Boolean>()
    override val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    override val errorMessage: LiveData<String> get() = _errorMessage

    override fun fetchBookingHistory(userId: Int) {
        _isLoading.value = true
        RetrofitClient.bookingInstance.getBookingHistory(userId)
            .enqueue(object : Callback<List<BookingHistory>> {
                override fun onResponse(call: Call<List<BookingHistory>>, response: Response<List<BookingHistory>>) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _bookings.value = response.body() ?: emptyList()
                    } else {
                        _errorMessage.value = "Lỗi tải lịch sử: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<List<BookingHistory>>, t: Throwable) {
                    _isLoading.value = false
                    _errorMessage.value = "Lỗi kết nối: ${t.localizedMessage}"
                }
            })
    }
}
