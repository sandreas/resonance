package com.pilabor.resonance.ui.feature.home

import com.pilabor.resonance.data.model.HomeDataResponse

sealed class HomeState {
    object Loading : HomeState()
    data class Success(val data: HomeDataResponse) : HomeState()
    data class Error(val message: String) : HomeState()
}