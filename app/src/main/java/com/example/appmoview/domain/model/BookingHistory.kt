package com.example.appmoview.domain.model

data class BookingHistory(
    val id: Int,
    val userId: Int,
    val roomId: Int,
    val showtimeId: Int,
    val price: Int,
    val status: String,

    // Bổ sung các trường để hiển thị
    val movieName: String,
    val movieType: String,
    val duration: String,
    val showDate: String,   // dạng "dd/MM/yyyy"
    val showTime: String    // dạng "HH:mm"
)
