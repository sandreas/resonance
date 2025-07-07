package com.codewithfk.musify_android.ui.feature.register

sealed class RegisterEvent {
    data class showErrorMessage(val message: String) : RegisterEvent()
    object NavigateToLogin : RegisterEvent()
    object NavigateToHome : RegisterEvent()
}