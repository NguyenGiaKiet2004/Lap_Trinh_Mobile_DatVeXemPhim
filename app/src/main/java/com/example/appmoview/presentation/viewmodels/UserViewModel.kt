package com.example.appmoview.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.appmoview.data.repository.UserRepositoryImpl
import com.example.appmoview.domain.model.UpdateProfileRequest
import com.example.appmoview.domain.model.User
import com.example.appmoview.domain.serviceInterface.UserRepository

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository = UserRepositoryImpl(application.applicationContext)

    val userProfile: LiveData<User?> = repository.userProfile
    val updateStatus: LiveData<Boolean?> = repository.updateStatus

    fun fetchUserProfile() {
        repository.getUserProfile()
    }

    fun updateUserProfile(fullName: String, phoneNumber: String, address: String) {
        val request = UpdateProfileRequest(fullName, phoneNumber, address)
        repository.updateUserProfile(request)
    }

    fun onUpdateStatusShown() {
        repository.clearUpdateStatus()
    }
}