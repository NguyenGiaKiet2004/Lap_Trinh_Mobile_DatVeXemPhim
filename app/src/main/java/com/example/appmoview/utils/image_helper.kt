package com.example.appmoview.utils

import com.example.appmoview.data.source.retrofit.RetrofitClient

object ImageHelper {
    fun getMovieImageUrl(filename: String): String {
        return "${RetrofitClient.getBaseUrl()}movie/file/$filename"
    }
}