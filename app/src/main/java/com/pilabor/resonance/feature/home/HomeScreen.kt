package com.pilabor.resonance.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pilabor.resonance.model.sampleMediaSources
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(modifier:Modifier=Modifier)  {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(sampleMediaSources) { it ->
            Column(modifier= Modifier.fillMaxWidth()) {
                Text(text="${it.name} (${it.id})", fontSize = 18.sp)
                Text(text="api ${it.api}", fontSize = 12.sp)
            }
        }

    }
}



