package com.example.appmoview.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.appmoview.data.repository.BookingRepositoryImpl


class BookingViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = BookingRepositoryImpl(application.applicationContext)

    val bookings = repository.bookings
    val rawBookings = repository.rawBookings
    val isLoading = repository.isLoading
    val errorMessage = repository.errorMessage

    fun loadBookings(userId:Int) {
        repository.fetchBookingHistory(userId)
    }
}

