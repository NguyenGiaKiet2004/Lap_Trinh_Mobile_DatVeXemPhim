package com.example.appmoview.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.appmoview.data.repository.AuthRepositoryImpl
import com.example.appmoview.domain.model.LoginRequest

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AuthRepositoryImpl(application.applicationContext)
    val loginStatus: LiveData<Boolean> = repository.loginStatus

    fun loginUser(user: LoginRequest) {
        repository.loginUser(user)
    }
}