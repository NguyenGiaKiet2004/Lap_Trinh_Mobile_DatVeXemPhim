package com.example.appmoview.utils

import com.example.appmoview.domain.model.MovieRequest


fun getActMovie(movies: List<MovieRequest>): List<MovieRequest> {
    val idList = listOf(1,2,3) // danh sách id có sẵn trong hàm
    return movies.filter { it.movie_id in idList }
}

fun getAnimeMovie(movies: List<MovieRequest>): List<MovieRequest> {
    val idList = listOf(1, 2, 4,5,6) // danh sách id có sẵn trong hàm
    return movies.filter { it.movie_id in idList }
}
