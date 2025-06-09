package com.example.appmoview.domain.model

data class RegisterRequest(
    val username: String,
    val password: String,
    val email: String,
    val role_id: Int
)
