package com.pilabor.resonance.ui.feature.register

sealed class RegisterEvent {
    data class showErrorMessage(val message: String) : RegisterEvent()
    object NavigateToLogin : RegisterEvent()
    object NavigateToHome : RegisterEvent()
}