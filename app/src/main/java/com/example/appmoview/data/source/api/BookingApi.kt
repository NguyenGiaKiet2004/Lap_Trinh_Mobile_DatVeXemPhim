package com.example.appmoview.data.source.api

import com.example.appmoview.domain.model.BookingHistory
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BookingApi {

    @GET("booking/history")
    fun getBookingHistory(@Query("userId") userId: Int): Call<List<BookingHistory>>
}