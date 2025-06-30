package com.pilabor.resonance

import BootstrapPauseCircle
import BootstrapPlayCircle
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pilabor.resonance.navigation.NavigationRoot
import com.pilabor.resonance.ui.theme.ResonanceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ResonanceTheme {
                Scaffold(
                    bottomBar = {
                        AppBottomBar()
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    NavigationRoot(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun AppBottomBar() {
    BottomAppBar(
        modifier = Modifier
            .height(75.dp)
            .padding(0.dp),
        actions = {
            Column(
                modifier=Modifier.padding(0.dp).fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {

                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    progress = { 1f },
                )


                Text("Test")


                // required buttons per mediatype:
                // Music: Play/Pause, Prev, Next, repeat, shuffle, heart
                // audiobook: play/pause, prev, next, back, forward, three dots with [speed, sleep-timer, chapter-listing ]

                // infos:
                // Music: artist, title, album, Duration/remaining time, minicover?, current-playlist?
                // audiobook: Title, Author, Series, minicover?
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        // .clip(RoundedCornerShape(100.dp))
                        modifier = Modifier.width(25.dp), // .padding(5.dp, 5.dp),
                        onClick = { /* do something */ },
                        //containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                        //elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(BootstrapPlayCircle, "Localized description")
                    }
                    FloatingActionButton(
                        modifier = Modifier.padding(5.dp),
                        onClick = { /* do something */ },
                        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(Icons.Outlined.PlayArrow, "Localized description")
                    }
                    IconToggleButton(checked = false, onCheckedChange = { }) {
                        if(false) {
                            Icon(BootstrapPauseCircle, contentDescription = "Localized description")
                        } else {
                            Icon(BootstrapPlayCircle, contentDescription = "Localized description")
                        }
                    }

                }

            }

            /*
            IconButton(onClick = { /* do something */ }) {
                Icon(Icons.Filled.Check, contentDescription = "Localized description")
            }
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = "Localized description",
                )
            }

             */
        }
        /*,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* do something */ },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.MoreVert, "Localized description")
            }
        }

         */
    )
}

@Preview
@Composable
fun AppBottomBarPreview() {
    AppBottomBar()
}


