package com.pilabor.resonance.feature.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(    modifier: Modifier = Modifier,
                     viewModel: DetailsViewModel = koinViewModel()) {
    val noteState by viewModel.noteState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            // .background(noteState.color)
            .padding(16.dp)
    ) {
        Text(
            text = noteState.name,
            fontSize = 26.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = noteState.api,
            fontSize = 18.sp
        )
    }
}

