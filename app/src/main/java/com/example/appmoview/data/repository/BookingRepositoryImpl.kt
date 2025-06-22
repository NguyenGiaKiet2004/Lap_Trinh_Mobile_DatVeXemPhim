package com.example.appmoview.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appmoview.data.source.retrofit.RetrofitClient
import com.example.appmoview.domain.model.BookingHistory
import com.example.appmoview.domain.model.RawBooking
import com.example.appmoview.domain.serviceInterface.BookingRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingRepositoryImpl(private val context: Context) : BookingRepository {



    private val _rawBookings = MutableLiveData<List<RawBooking>>()
    override val rawBookings: LiveData<List<RawBooking>> get() = _rawBookings

    private val _bookings = MutableLiveData<List<BookingHistory>>()
    override val bookings: LiveData<List<BookingHistory>>
        get() = _bookings

    private val _isLoading = MutableLiveData<Boolean>()
    override val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    override val errorMessage: LiveData<String> get() = _errorMessage

    override fun fetchBookingHistory(userId:Int) {
        _isLoading.value = true
        RetrofitClient.bookingInstance.getBookingHistory(userId)
            .enqueue(object : Callback<List<RawBooking>> {
                override fun onResponse(call: Call<List<RawBooking>>, response: Response<List<RawBooking>>) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _rawBookings.value = response.body() ?: emptyList()
                    } else {
                        _errorMessage.value = "Lỗi tải lịch sử: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<List<RawBooking>>, t: Throwable) {
                    _isLoading.value = false
                    _errorMessage.value = "Lỗi kết nối: ${t.localizedMessage}"
                }
            })
    }
}
