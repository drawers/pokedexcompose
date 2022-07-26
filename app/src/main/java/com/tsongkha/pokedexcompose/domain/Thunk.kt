package com.tsongkha.pokedexcompose

import org.reduxkotlin.Dispatcher
import org.reduxkotlin.GetState
import org.reduxkotlin.Middleware

internal typealias Thunk<State> = (dispatch: Dispatcher, getState: GetState<State>) -> Any

internal fun <State> createThunkMiddleware(): Middleware<State> =
    { store ->
        { next: Dispatcher ->
            { action: Any ->
                if (action is Function<*>) {
                    @Suppress("UNCHECKED_CAST")
                    val thunk = try {
                        (action as Thunk<State>)
                    } catch (e: ClassCastException) {
                        throw IllegalArgumentException(
                            "Dispatching functions must use type Thunk:",
                            e
                        )
                    }

                    thunk(store.dispatch, store.getState)
                } else {
                    next(action)
                }
            }
        }
    }