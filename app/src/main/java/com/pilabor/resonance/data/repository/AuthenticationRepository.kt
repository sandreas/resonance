package com.codewithfk.musify_android.data.repository

import com.codewithfk.musify_android.data.model.LoginRequest
import com.codewithfk.musify_android.data.model.LoginResponse
import com.codewithfk.musify_android.data.model.RegisterRequest
import com.codewithfk.musify_android.data.network.ApiService
import com.codewithfk.musify_android.data.network.Resource
import org.koin.core.annotation.Single

@Single
class AuthenticationRepository(
    private val apiService: ApiService
) {

    suspend fun login(loginRequest: LoginRequest): Resource<LoginResponse> {
        return try {
            val response = apiService.login(loginRequest)
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Login failed")
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.message}")
        }
    }

    suspend fun register(registerRequest: RegisterRequest): Resource<LoginResponse> {
        return try {
            val response = apiService.register(registerRequest)
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Registration failed")
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.message}")
        }
    }
}