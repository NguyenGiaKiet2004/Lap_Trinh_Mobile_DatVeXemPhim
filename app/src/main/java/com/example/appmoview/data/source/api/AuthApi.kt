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

    @POST("login/sign_up") // Đường dẫn API PHP của bạn
    fun login(@Body request: LoginRequest): Call<ResponseData>

    @POST("login/register")
    fun registerUser(@Body request: RegisterRequest): Call<ResponseData>

}