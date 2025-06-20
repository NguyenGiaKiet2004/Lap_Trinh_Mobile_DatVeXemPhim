package com.example.appmoview.domain.serviceInterface

import androidx.lifecycle.LiveData
import com.example.appmoview.domain.model.UpdateProfileRequest
import com.example.appmoview.domain.model.User

interface UserRepository {
    // LiveData để UI lắng nghe thông tin người dùng
    val userProfile: LiveData<User?>
    // LiveData để báo trạng thái cập nhật thành công hay thất bại
    val updateStatus: LiveData<Boolean?>

    fun getUserProfile()
    fun updateUserProfile(request: UpdateProfileRequest)
    fun clearUpdateStatus()
}