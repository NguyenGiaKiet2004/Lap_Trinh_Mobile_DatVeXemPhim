package com.example.appmoview.domain.model

data class UpdateProfileRequest(
    val fullName: String,
    val phoneNumber: String,
    val address: String
)

