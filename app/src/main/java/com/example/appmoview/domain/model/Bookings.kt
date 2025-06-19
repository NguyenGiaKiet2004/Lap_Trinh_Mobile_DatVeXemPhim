package com.example.appmoview.domain.model

data class BookingRequest(
    val userId: Int,
    val showtimeId: Int,
    val seatIds: List<Int>
)
