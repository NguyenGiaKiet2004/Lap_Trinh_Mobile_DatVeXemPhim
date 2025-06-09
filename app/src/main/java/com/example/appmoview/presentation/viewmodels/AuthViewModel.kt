package com.example.appmoview.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.appmoview.data.repository.AuthRepositoryImpl
import com.example.appmoview.domain.model.LoginRequest
import com.example.appmoview.domain.model.RegisterRequest
import com.example.appmoview.domain.model.ResponseData


class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AuthRepositoryImpl(application.applicationContext)
    val loginStatus: LiveData<Boolean> = repository.loginStatus
    val registerStatus: LiveData<ResponseData> = repository.registerStatus

    fun loginUser(user: LoginRequest) {
        repository.loginUser(user)
    }

    fun register(request: RegisterRequest) {
        repository.registerUser(request)
    }
}