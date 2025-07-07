package com.pilabor.resonance.data.repository

import com.pilabor.resonance.data.model.HomeDataResponse
import com.pilabor.resonance.data.network.ApiService
import com.pilabor.resonance.data.network.Resource
import org.koin.core.annotation.Single

@Single
class HomeRepository(private val apiService: ApiService) {

    suspend fun getHomeData(): Resource<HomeDataResponse> {
        return try {
            val response = apiService.getHomeData()
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Failed to fetch home data")
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.message}")
        }
    }
}