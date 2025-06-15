package com.example.appmoview.domain.model

data class MovieRequest(
    val movie_id: Int,
    val movie_name: String,
    val movie_description: String,
    val movie_picture: String,
    val movie_trailer: String,
    val movie_time: Int,
    val movie_price:Int
)