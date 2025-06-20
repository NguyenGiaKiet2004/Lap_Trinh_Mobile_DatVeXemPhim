package com.example.appmoview.presentation.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.appmoview.data.repository.BookingRepositoryImpl
import com.example.appmoview.domain.model.BookingHistory

class BookingViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = BookingRepositoryImpl(application.applicationContext)

    val bookings = repository.bookings
    val isLoading = repository.isLoading
    val errorMessage = repository.errorMessage

    fun loadBookings(userId: Int) {
        repository.fetchBookingHistory(userId)
    }
}
