package com.codewithfk.musify_android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithfk.musify_android.data.MusifySession
import com.codewithfk.musify_android.data.repository.StatusRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(val repository: StatusRepository, val musifySession: MusifySession) :
    ViewModel() {

    private val state = MutableStateFlow("")
    val status = state.asStateFlow()

    init {
        getStatus()
    }

    fun isUserLoggedIn(): Boolean {
        return musifySession.getToken() != null
    }

    private fun getStatus() {
        viewModelScope.launch {
            val result = repository.getStatus()
            state.value = result
        }
    }
}