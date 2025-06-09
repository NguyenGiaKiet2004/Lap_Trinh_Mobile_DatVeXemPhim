package com.example.appmoview.data.source.api


import com.example.appmoview.domain.model.LoginRequest
import com.example.appmoview.domain.model.RegisterRequest
import com.example.appmoview.domain.model.ResponseData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("login") // Đường dẫn API PHP của bạn
    fun login(@Body request: LoginRequest): Call<ResponseBody>

    @POST("login/register")
    fun registerUser(@Body request: RegisterRequest): Call<ResponseData>

}