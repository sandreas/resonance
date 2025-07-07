package com.codewithfk.musify_android.ui.feature.onboarding

sealed class OnboardingEvent {
    data class showErrorMessage(val message: String) : OnboardingEvent()
    object NavigateToLogin : OnboardingEvent()
}