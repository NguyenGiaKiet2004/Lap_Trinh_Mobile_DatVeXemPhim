package com.example.appmoview.domain.model

data class BookingHistory(
    val id: Int,
    val movieName: String,
    val movieType: String,
    val duration: String,
    val showTime: String,
    val showDate: String
)