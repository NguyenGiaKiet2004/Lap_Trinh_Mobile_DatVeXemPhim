package com.example.appmoview.domain.model

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponseData(
    val userId: Int,
    val username: String,
    val token: String
)