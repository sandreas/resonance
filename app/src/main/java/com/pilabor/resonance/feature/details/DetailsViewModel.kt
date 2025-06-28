package com.pilabor.resonance.feature.details

import androidx.lifecycle.ViewModel
import com.pilabor.resonance.model.sampleMediaSources
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailsViewModel(private val noteId: String): ViewModel() {
    private val _noteState = MutableStateFlow(
        sampleMediaSources.first { it.id == noteId }
    )
    val noteState = _noteState.asStateFlow()
}