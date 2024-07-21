package com.training.codespire.data.repos

import android.content.Context
import com.training.codespire.data.datastore.SharedPreferencesUtil
import com.training.codespire.network.ApiService
import com.training.codespire.network.auth.LoginRequest
import com.training.codespire.network.auth.LoginResponse
import com.training.codespire.network.auth.RegisterRequest
import com.training.codespire.network.auth.RegisterResponse
import com.training.codespire.network.RetrofitClient
import retrofit2.Response

class AuthRepository(private val context: Context) {


    private val api: ApiService = RetrofitClient.apiService
    private val sharedPreferencesUtil = SharedPreferencesUtil(context)


    suspend fun registerUser(registerRequest: RegisterRequest): Response<RegisterResponse> {
        return api.registerUser(registerRequest)
    }

    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse> {
        val response = api.loginUser(loginRequest)
        if (response.isSuccessful) {
            response.body()?.let { loginResponse ->
                sharedPreferencesUtil.token = loginResponse.token
                sharedPreferencesUtil.username = loginResponse.user.name
                sharedPreferencesUtil.isLoggedIn = true
            }
        }
        return response
    }

    suspend fun logoutUser(): Response<Void> {
        val token = sharedPreferencesUtil.token
        val response = api.logoutUser("Bearer $token")
        if (response.isSuccessful) {
            sharedPreferencesUtil.clear()
        }
        return response
    }


}