package com.tsongkha.pokedexcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tsongkha.pokedexcompose.domain.Action
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.reduxkotlin.Store
import org.reduxkotlin.applyMiddleware
import org.reduxkotlin.combineReducers
import org.reduxkotlin.createThreadSafeStore

/**
 * Composition local providing convenient access to the store dispatch function
 */
val LocalDispatch = compositionLocalOf<(Any) -> Any> { error("No default dispatch") }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: PokedexViewModel = viewModel()

            val finishActivity = viewModel.finishActivityTrigger.collectAsState(initial = false)
            if (finishActivity.value) {
                finish()
            }

            DisposableEffect(onBackPressedDispatcher) {
                val callback = object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        viewModel.store.dispatch(Action.Navigation.Back)
                    }
                }

                onBackPressedDispatcher.addCallback(callback)

                onDispose {
                    callback.remove()
                }
            }

            CompositionLocalProvider(LocalDispatch provides viewModel.store.dispatch) {
                val state = viewModel.state.collectAsState()
                App(appState = state.value)
            }
        }
    }
}

internal class PokedexViewModel : ViewModel() {

    val finishActivityTrigger: Flow<Boolean> = MutableStateFlow(false)

    val store: Store<AppState> = createApp(
        viewModelScope
    ) {
        (finishActivityTrigger as MutableStateFlow).compareAndSet(
            expect = false,
            update = true
        )
    }

    private val stateFlow = MutableStateFlow(store.state)
    val state: StateFlow<AppState> = stateFlow

    init {
        // This isn't ideal, but it's a way to escape the lack of crossinline on the subscribe callback.
        // There's a very slim chance this could race and suspend forever.
        // We'll cross that bridge when we come to it :shrug:
        store.subscribe {
            stateFlow.compareAndSet(stateFlow.value, store.state)
        }
    }

    override fun onCleared() {
        destroyApp()
    }

    /**
     * [createApp] wires up all the necessary Redux components, returning the [Store] to the consuming
     * frontend.
     */
    private fun createApp(
        applicationScope: CoroutineScope,
        closeApp: () -> Unit,
    ): Store<AppState> {
        startKoin {
            modules(applicationModule(applicationScope))
        }

        val reducer = combineReducers(
            NavigationReducer,
        )

        val middleware = applyMiddleware(
            backNavigationMiddleware(closeApp),
            createThunkMiddleware(),
        )

        val initialState = AppState(
            pokemon = emptyList(),
            navigation = Navigation(
                emptyList(),
                currentScreen = Screen.Grid
            ),
        )

        return createThreadSafeStore(reducer, initialState, middleware)
    }

    /**
     * [destroyApp] does any tidy-up necessary, when shutting down the app under normal operating conditions
     */
    private fun destroyApp() {
        stopKoin()
    }
}
