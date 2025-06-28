package com.pilabor.resonance.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.pilabor.resonance.screens.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeScreenNavKey: NavKey

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(HomeScreenNavKey)
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
            rememberSceneSetupNavEntryDecorator()
        ),
        // sceneStrategy = TwoPaneSceneStrategy(),
        entryProvider = { key ->
            when(key) {
                is HomeScreenNavKey -> {
                    NavEntry(
                        key = key,
                        // metadata = TwoPaneScene.twoPane()
                    ) {
                        HomeScreen()
                    }
                }
                /*
                is NoteDetailScreen -> {
                    NavEntry(
                        key = key,
                        metadata = TwoPaneScene.twoPane()
                    ) {
                        NoteDetailScreenUi(
                            viewModel = koinViewModel {
                                parametersOf(key.id)
                            }
                        )
                    }
                }

                 */
                else -> throw RuntimeException("Invalid NavKey.")
            }
        },
    )
}