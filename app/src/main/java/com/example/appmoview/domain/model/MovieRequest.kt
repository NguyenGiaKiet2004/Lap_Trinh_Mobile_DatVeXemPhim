package com.example.appmoview.domain.model

data class MovieRequest(
    val id: Int,
    val movie_name: String,
    val movie_description: String,
    val movie_time: Int,
    val movie_image: String
)