package com.example.appmoview.domain.serviceInterface

import com.example.appmoview.domain.model.LoginRequest


interface AuthRepository {
    fun loginUser(user: LoginRequest)
}