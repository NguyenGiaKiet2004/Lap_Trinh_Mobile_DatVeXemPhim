package com.example.appmoview.data.source.api

import com.example.appmoview.domain.model.BookingHistory
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BookingApi {

    @GET("booking/get/{userId}")
    fun getBookingHistory(@Path("userId") userId: Int): Call<List<BookingHistory>>

}