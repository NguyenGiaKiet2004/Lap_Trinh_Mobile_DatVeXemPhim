package com.example.appmoview.domain.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: String?,

    @SerializedName("fullName") // Đổi tên cho phù hợp với JSON từ API
    val fullName: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("phoneNumber")
    val phoneNumber: String?,

    @SerializedName("address")
    val address: String?
)