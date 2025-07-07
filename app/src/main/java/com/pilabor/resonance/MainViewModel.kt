package com.pilabor.resonance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pilabor.resonance.data.SettingsStorage
import com.pilabor.resonance.data.repository.StatusRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(val repository: StatusRepository, val resonanceSession: SettingsStorage) :
    ViewModel() {

    private val state = MutableStateFlow("")
    val status = state.asStateFlow()

    init {
        getStatus()
    }

    fun isUserLoggedIn(): Boolean {
        return resonanceSession.getToken() != null
    }

    private fun getStatus() {
        viewModelScope.launch {
            val result = repository.getStatus()
            state.value = result
        }
    }
}