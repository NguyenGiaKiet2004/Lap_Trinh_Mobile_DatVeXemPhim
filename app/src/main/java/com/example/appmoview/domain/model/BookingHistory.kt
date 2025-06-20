package com.example.appmoview.domain.model


data class BookingHistory(
    val booking_id: Int,
    val user_id: Int,
    val room_id: Int,
    val showtime_id: Int,
    val price: Int,
    val booking_status: String
)
