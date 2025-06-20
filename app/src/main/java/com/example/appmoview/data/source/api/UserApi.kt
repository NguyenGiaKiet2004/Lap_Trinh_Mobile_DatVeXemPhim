package com.example.appmoview.data.source.api

import com.example.appmoview.domain.model.ResponseData // Bạn có thể tái sử dụng ResponseData
import com.example.appmoview.domain.model.UpdateProfileRequest
import com.example.appmoview.domain.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

interface UserApi {

    // API để lấy thông tin người dùng
    @GET("api/users/me") // Giả sử endpoint là /api/users/me
    fun getUserProfile(
        @Header("Authorization") token: String
    ): Call<ResponseData> // Bọc User trong ResponseData nếu API trả về cấu trúc chung

    // API để cập nhật thông tin người dùng
    @PUT("api/users/me")
    fun updateUserProfile(
        @Header("Authorization") token: String,
        @Body request: UpdateProfileRequest
    ): Call<ResponseData>
}