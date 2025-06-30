package com.pilabor.resonance

import BootstrapFastForwardCircle
import BootstrapPlayCircle
import BootstrapRewindCircle
import BootstrapSkipEndCircle
import BootstrapSkipStartCircle
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ResonanceTheme {
                Scaffold(
                    bottomBar = {
                        AppBottomBar(mainViewModel)
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
fun AppBottomBar(mainViewModel: MainViewModel?) {
    BottomAppBar(
        modifier = Modifier
            // min height to show everything correctly
            .height(IntrinsicSize.Min) // set to 0.dp to hide
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


                Row(
                    modifier=Modifier.fillMaxWidth().padding(0.dp),
                    horizontalArrangement = Arrangement.Absolute.Left
                ) {
                    Text(modifier = Modifier, text = "Test")
                }



                // required buttons per mediatype:
                // Music: Play/Pause, Prev, Next, repeat, shuffle, heart
                // audiobook: play/pause, prev, next, back, forward, three dots with [speed, sleep-timer, chapter-listing ]

                // infos:
                // Music: artist, title, album, Duration/remaining time, minicover?, current-playlist?
                // audiobook: Title, Author, Series, minicover?
                Row(modifier=Modifier.padding(0.dp), verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        // .clip(RoundedCornerShape(100.dp))
                        modifier = Modifier.padding(0.dp).fillMaxHeight(), // .padding(5.dp, 5.dp),
                        onClick = {
                            mainViewModel?.onSkipStart()
                        },
                        //containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                        //elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(modifier = Modifier.size(40.dp), imageVector = BootstrapSkipStartCircle, contentDescription = "Skip start")
                    }
                    IconButton(
                        modifier = Modifier.padding(0.dp).fillMaxHeight(),
                        onClick = { /* do something */ },
                    ) {
                        Icon(modifier = Modifier.size(40.dp), imageVector = BootstrapRewindCircle, contentDescription = "Skip start")
                    }
                    IconButton(
                        modifier = Modifier.padding(0.dp).fillMaxHeight(),
                        onClick = { /* do something */ },
                    ) {
                        Icon(modifier = Modifier.size(40.dp), imageVector = BootstrapPlayCircle, contentDescription = "Skip start")
                    }
                    IconButton(
                        modifier = Modifier.padding(0.dp).fillMaxHeight(),
                        onClick = { /* do something */ },
                    ) {
                        Icon(modifier = Modifier.size(40.dp), imageVector = BootstrapFastForwardCircle, contentDescription = "Skip start")
                    }
                    IconButton(
                        modifier = Modifier.padding(0.dp).fillMaxHeight(),
                        onClick = { /* do something */ },
                    ) {
                        Icon(modifier = Modifier.size(40.dp), imageVector = BootstrapSkipEndCircle, contentDescription = "Skip start")
                    }

                    /*
                    IconToggleButton(checked = false, onCheckedChange = { }) {
                        if(false) {
                            Icon(BootstrapPauseCircle, contentDescription = "Localized description")
                        } else {
                            Icon(BootstrapPlayCircle, contentDescription = "Localized description")
                        }
                    }
                    */
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
    AppBottomBar(mainViewModel = null)
}


