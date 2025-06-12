package com.example.appmoview.data.source.api


import com.example.appmoview.domain.model.LoginRequest
import com.example.appmoview.domain.model.RegisterRequest
import com.example.appmoview.domain.model.ResponseData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @FormUrlEncoded
    @POST("login/sign_up")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<ResponseData>

    @POST("login/register")
    fun registerUser(@Body request: RegisterRequest): Call<ResponseData>

}