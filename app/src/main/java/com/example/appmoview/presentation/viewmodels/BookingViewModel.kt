package com.example.appmoview.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.appmoview.data.repository.BookingRepositoryImpl
import com.example.appmoview.domain.model.BookingHistory

class BookingViewModel(private val context: Context) : ViewModel() {

    private val repository = BookingRepositoryImpl(context)

    val bookings: LiveData<List<BookingHistory>> = repository.bookings
    val isLoading: LiveData<Boolean> = repository.isLoading
    val errorMessage: LiveData<String> = repository.errorMessage

    init {
        loadBookingHistory()
    }

    fun loadBookingHistory() {
        val sharedPref = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val userId = sharedPref.getString("user_id", null)?.toIntOrNull()
        if (userId != null) {
            repository.fetchBookingHistory(userId)
        }
    }
}