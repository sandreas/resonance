package com.pilabor.resonance.ui.feature.login

sealed class LoginState {
    object Nothing : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}