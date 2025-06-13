package com.example.appmoview.domain.model

import java.util.Date

data class TicketRequest(
    val id: Int,
    val movie_name: String,
    val movie_genre: String,
    val movie_time: Int,
    val movie_showtime: Int,
    val booking_time: Date
)