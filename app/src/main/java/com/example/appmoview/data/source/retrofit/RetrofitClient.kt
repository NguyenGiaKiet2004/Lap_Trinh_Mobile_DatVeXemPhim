package com.example.appmoview.data.source.retrofit

import com.example.appmoview.data.source.api.AuthApi
import com.example.appmoview.data.source.api.BookingApi
import com.example.appmoview.data.source.api.MovieApi
import com.example.appmoview.data.source.api.UserApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {


    // sửa địa chỉ ip hiện tại của máy
    private const val BASE_URL = "http://192.168.1.23:8080/"

    fun getBaseUrl(): String { return BASE_URL}

    val instance: AuthApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    val movieInstance: MovieApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }

    val bookingInstance: BookingApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookingApi::class.java)
    }

    // ✅ Thêm instance mới cho UserApi
    val userInstance: UserApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }

}