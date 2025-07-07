package com.codewithfk.musify_android.ui.feature.login

sealed class LoginEvent {
    data class showErrorMessage(val message: String) : LoginEvent()
    object NavigateToRegister : LoginEvent()
    object NavigateToHome : LoginEvent()
}