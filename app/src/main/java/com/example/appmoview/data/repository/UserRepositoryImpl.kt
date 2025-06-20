package com.example.appmoview.data.repository



import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appmoview.data.source.retrofit.RetrofitClient
import com.example.appmoview.domain.model.ResponseData
import com.example.appmoview.domain.model.UpdateProfileRequest
import com.example.appmoview.domain.model.User
import com.example.appmoview.domain.serviceInterface.UserRepository
import com.google.gson.Gson // <<< THÊM IMPORT NÀY
import com.google.gson.reflect.TypeToken // <<< THÊM IMPORT NÀY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepositoryImpl(private val context: Context) : UserRepository {

    private val _userProfile = MutableLiveData<User?>()
    override val userProfile: LiveData<User?> get() = _userProfile

    private val _updateStatus = MutableLiveData<Boolean?>()
    override val updateStatus: LiveData<Boolean?> get() = _updateStatus

    private val gson = Gson() // Khởi tạo một đối tượng Gson để tái sử dụng

    private fun getToken(): String? {
        val sharedPref = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        return sharedPref.getString("auth_token", null)
    }

    // Hàm tiện ích để chuyển đổi từ Any? sang User
    private fun convertDataToUser(data: Any?): User? {
        if (data == null) return null
        return try {
            // Chuyển đối tượng data (thường là LinkedTreeMap) về chuỗi JSON
            val json = gson.toJson(data)
            // Lấy kiểu của đối tượng User
            val userType = object : TypeToken<User>() {}.type
            // Dùng chuỗi JSON để tạo lại đối tượng User
            gson.fromJson(json, userType)
        } catch (e: Exception) {
            Log.e("UserRepository", "Lỗi chuyển đổi dữ liệu sang User", e)
            null
        }
    }

    override fun getUserProfile() {
        val token = getToken()
        if (token == null) {
            _userProfile.postValue(null)
            return
        }

        // Kiểu callback bây giờ là ResponseData, không phải ResponseData<User>
        RetrofitClient.userInstance.getUserProfile("Bearer $token")
            .enqueue(object : Callback<ResponseData> {
                override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody?.success == true) {
                        // BƯỚC CHUYỂN ĐỔI THỦ CÔNG
                        val user = convertDataToUser(responseBody.data)
                        _userProfile.postValue(user)
                    } else {
                        Log.e("UserRepository", "Lỗi lấy profile: ${response.code()}")
                        _userProfile.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    Log.e("UserRepository", "Lỗi mạng khi lấy profile", t)
                    _userProfile.postValue(null)
                }
            })
    }

    override fun updateUserProfile(request: UpdateProfileRequest) {
        val token = getToken()
        if (token == null) {
            _updateStatus.postValue(false)
            return
        }

        RetrofitClient.userInstance.updateUserProfile("Bearer $token", request)
            .enqueue(object : Callback<ResponseData> {
                override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody?.success == true) {
                        // BƯỚC CHUYỂN ĐỔI THỦ CÔNG
                        val updatedUser = convertDataToUser(responseBody.data)
                        _userProfile.postValue(updatedUser) // Cập nhật lại profile
                        _updateStatus.postValue(true)
                    } else {
                        Log.e("UserRepository", "Cập nhật thất bại: ${response.code()}")
                        _updateStatus.postValue(false)
                    }
                }

                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    Log.e("UserRepository", "Lỗi mạng khi cập nhật profile", t)
                    _updateStatus.postValue(false)
                }
            })
    }

    override fun clearUpdateStatus() {
        _updateStatus.value = null
    }
}