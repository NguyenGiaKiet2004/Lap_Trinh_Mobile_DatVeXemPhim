package com.example.appmoview.utils

import com.example.appmoview.domain.model.MovieRequest


fun getActMovie(movies: List<MovieRequest>): List<MovieRequest> {
    val idList = listOf(1, 3, 5) // danh sách id có sẵn trong hàm
    return movies.filter { it.movie_id in idList }
}

fun getAnimeMovie(movies: List<MovieRequest>): List<MovieRequest> {
    val idList = listOf(1, 3, 5) // danh sách id có sẵn trong hàm
    return movies.filter { it.movie_id in idList }
}
