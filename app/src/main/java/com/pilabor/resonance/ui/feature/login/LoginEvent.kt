package com.pilabor.resonance.ui.feature.login

sealed class LoginEvent {
    data class showErrorMessage(val message: String) : LoginEvent()
    object NavigateToRegister : LoginEvent()
    object NavigateToHome : LoginEvent()
}