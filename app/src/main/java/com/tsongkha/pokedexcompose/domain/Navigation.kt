package com.tsongkha.pokedexcompose

import com.tsongkha.pokedexcompose.domain.Action
import org.reduxkotlin.middleware
import org.reduxkotlin.reducerForActionType

internal data class Navigation(
    val backstack: List<Screen>,
    val currentScreen: Screen,
)

internal sealed class Screen {
    object Grid : Screen()

    object Details : Screen()
}

internal fun backNavigationMiddleware(exitApplication: () -> Unit) =
    middleware<AppState> { store, next, action ->
        if (action == Action.Navigation.Back && store.state.navigation.backstack.isEmpty()) {
            exitApplication()
        } else {
            next(action)
        }
    }

internal val NavigationReducer =
    reducerForActionType<AppState, Action.Navigation> { state, action ->
        when (action) {
            Action.Navigation.Back -> state.pop()
            is Action.Navigation.To -> {
                if (state.navigation.currentScreen is Screen.Details && action.screen is Screen.Grid) {
                    state.push(Screen.Details)
                } else {
                    state
                }
            }
        }
    }

private fun AppState.replace(screen: Screen): AppState =
    copy(navigation = navigation.copy(currentScreen = screen))

private fun AppState.push(screen: Screen): AppState = copy(
    navigation = navigation.copy(
        backstack = listOf(*navigation.backstack.toTypedArray(), navigation.currentScreen),
        currentScreen = screen
    )
)

private fun AppState.pop(): AppState = copy(
    navigation = navigation.copy(
        backstack = navigation.backstack.dropLast(1),
        currentScreen = navigation.backstack.last()
    )
)
