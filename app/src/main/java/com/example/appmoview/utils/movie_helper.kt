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

fun formatDateTimeRaw(datetime: String): String {
    return try {
        val parts = datetime.split("T") // ["2025-06-19", "18:30:00"]
        val date = parts[0].split("-")  // ["2025", "06", "19"]
        val time = parts[1].substring(0, 5) // "18:30"
        "${date[2]}/${date[1]}/${date[0]} - $time" // "19/06/2025 - 18:30"
    } catch (e: Exception) {
        ""
    }
}

