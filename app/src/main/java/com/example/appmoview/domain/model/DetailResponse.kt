package com.example.appmoview.domain.model

data class ApiResponse<T>(
    val desc: String?,
    val data: T?,
    val status: Int,
    val success: Boolean
)

data class MovieDetail(
    val movie_id: Int,
    val movie_name: String,
    val movie_description: String,
    val movie_picture: String,
    val movie_trailer: String,
    val movie_time: Int,
    val movie_price: Int,
    val genres: List<String> // ← Thêm dòng này
)

data class Showtime(
    val showtimeId: Int,
    val startTime: String,
    val endTime: String,
    val rooms: Room,
    val movies: MovieDetail
)

data class Room(
    val room_id: Int,
    val room_name: String,
    val description: String,
    val capacity: Int
)