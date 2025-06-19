package com.example.appmoview.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appmoview.data.source.retrofit.RetrofitClient
import com.example.appmoview.domain.model.LoginRequest
import com.example.appmoview.domain.model.LoginResponseData
import com.example.appmoview.domain.model.RegisterRequest
import com.example.appmoview.domain.model.ResponseData
import com.example.appmoview.domain.serviceInterface.AuthRepository
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap


class AuthRepositoryImpl(private val context: Context) : AuthRepository {


    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> get() = _loginStatus

    private val _registerStatus = MutableLiveData<ResponseData>()
    val registerStatus: LiveData<ResponseData> get() = _registerStatus

    override fun loginUser(user: LoginRequest) {
        RetrofitClient.instance.login(user.username, user.password)
            .enqueue(object : Callback<ResponseData> {
                override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                    if (response.isSuccessful) {
                        val responseData = response.body()
                        if (responseData != null && responseData.success) {
                            _loginStatus.value = true

                            // ⚠️ Parse data về LoginResponseData bằng Gson
                            val gson = Gson()
                            val jsonData = gson.toJson(responseData.data)
                            val loginData = gson.fromJson(jsonData, LoginResponseData::class.java)

                            val token = loginData.token
                            val userId = loginData.userId.toString()
                            val username = loginData.username

                            // ✅ Lưu vào SharedPreferences
                            val sharedPref = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
                            sharedPref.edit()
                                .putString("auth_token", token)
                                .putString("username", username)
                                .putString("user_id", userId)
                                .apply()
                        } else {
                            Log.e("LoginRepository", "Đăng nhập thất bại")
                            _loginStatus.value = false
                        }
                    } else {
                        Log.e("LoginRepository", "Lỗi phía server: ${response.code()}")
                        _loginStatus.value = false
                    }
                }

                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    Log.e("LoginRepository", "Lỗi mạng", t)
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