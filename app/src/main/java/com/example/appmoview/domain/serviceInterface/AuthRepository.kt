package com.example.appmoview.domain.serviceInterface

import androidx.lifecycle.LiveData
import com.example.appmoview.domain.model.LoginRequest
import com.example.appmoview.domain.model.RegisterRequest
import com.example.appmoview.domain.model.ResponseData


interface AuthRepository {
    fun loginUser(user: LoginRequest)
    fun registerUser(request: RegisterRequest)
}