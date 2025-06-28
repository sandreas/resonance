package com.pilabor.resonance.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.pilabor.resonance.feature.details.DetailScreen
import com.pilabor.resonance.feature.details.DetailsNavKey
import com.pilabor.resonance.feature.home.HomeNavKey
import com.pilabor.resonance.feature.home.HomeScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(HomeNavKey)
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
            // todo: HomeNavEntry : NavEntry
            when(key) {
                is HomeNavKey -> {
                    NavEntry(
                        key = key,
                        // metadata = TwoPaneScene.twoPane()
                    ) {
                        HomeScreen(onDetailClick = { id ->
                            backStack.add(DetailsNavKey(id))
                        })
                    }
                }

                is DetailsNavKey -> {
                    NavEntry(
                        key = key,
                        // metadata = TwoPaneScene.twoPane()
                    ) {
                        DetailScreen(
                            viewModel = koinViewModel {
                                parametersOf(key.id)
                            }
                        )
                    }
                }
                else -> throw RuntimeException("Invalid NavKey.")
            }
        },
    )
}