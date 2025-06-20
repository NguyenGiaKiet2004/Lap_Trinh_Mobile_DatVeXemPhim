package com.example.appmoview.domain.serviceInterface

import androidx.lifecycle.LiveData
import com.example.appmoview.domain.model.BookingHistory

interface BookingRepository {
    fun fetchBookingHistory(userId: Int)
    val bookings: LiveData<List<BookingHistory>>
    val isLoading: LiveData<Boolean>
    val errorMessage: LiveData<String>
}