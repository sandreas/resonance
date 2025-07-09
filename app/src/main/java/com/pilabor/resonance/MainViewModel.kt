package com.pilabor.resonance

import androidx.lifecycle.ViewModel
import com.pilabor.resonance.data.SettingsStorage
import org.koin.android.annotation.KoinViewModel

class MainViewModel(val resonanceSession: SettingsStorage) :
    ViewModel() {

}