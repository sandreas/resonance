package com.pilabor.resonance
import android.util.Log
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    fun onSkipStart() {
        Log.d(this::class.simpleName, "onSkipStart")
    }
}