package com.example.appmoview.domain.serviceInterface

import androidx.lifecycle.LiveData
import com.example.appmoview.domain.model.BookingHistory
import com.example.appmoview.domain.model.RawBooking

interface BookingRepository {
    val bookings: LiveData<List<BookingHistory>>
    val rawBookings: LiveData<List<RawBooking>>
    val isLoading: LiveData<Boolean>
    val errorMessage: LiveData<String>

    fun fetchBookingHistory(userId:Int)
}
