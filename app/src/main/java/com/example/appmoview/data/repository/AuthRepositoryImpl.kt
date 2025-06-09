package com.example.appmoview.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appmoview.data.source.retrofit.RetrofitClient
import com.example.appmoview.domain.model.LoginRequest
import com.example.appmoview.domain.model.RegisterRequest
import com.example.appmoview.domain.model.ResponseData
import com.example.appmoview.domain.serviceInterface.AuthRepository
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepositoryImpl(private val context: Context) : AuthRepository {


    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> get() = _loginStatus

    private val _registerStatus = MutableLiveData<ResponseData>()
    val registerStatus: LiveData<ResponseData> get() = _registerStatus

    override fun loginUser(user: LoginRequest) {
        RetrofitClient.instance.login(user).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    try {
                        val responseData = response.body()?.string()
                        Log.d("LoginRepository", "Response data: $responseData")

                        val jsonObject = JSONObject(responseData ?: "{}")
                        if (jsonObject.getBoolean("success")) {
                            _loginStatus.value = true
                        } else {
                            Log.e("LoginRepository", "Login failed: ${jsonObject.getString("message")}")
                            _loginStatus.value = false
                        }
                    } catch (e: Exception) {
                        Log.e("LoginRepository", "Error parsing JSON response", e)
                        _loginStatus.value = false
                    }
                } else {
                    Log.e("LoginRepository", "Server returned error: ${response.code()}")
                    _loginStatus.value = false
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("LoginRepository", "Network request failed", t)
                _loginStatus.value = false
            }
        })
    }

    override fun registerUser(request: RegisterRequest) {
        RetrofitClient.instance.registerUser(request).enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if (response.isSuccessful) {
                    _registerStatus.value = response.body()
                } else {
                    _registerStatus.value = ResponseData(false, "Đăng ký thất bại")
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                _registerStatus.value = ResponseData(false, t.message ?: "Lỗi kết nối")
            }
        })
    }

}