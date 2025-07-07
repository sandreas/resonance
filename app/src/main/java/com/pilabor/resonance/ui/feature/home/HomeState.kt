package com.codewithfk.musify_android.ui.feature.home

import com.codewithfk.musify_android.data.model.HomeDataResponse

sealed class HomeState {
    object Loading : HomeState()
    data class Success(val data: HomeDataResponse) : HomeState()
    data class Error(val message: String) : HomeState()
}