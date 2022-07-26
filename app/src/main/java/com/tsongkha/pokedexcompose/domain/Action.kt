package com.tsongkha.pokedexcompose.domain

import com.tsongkha.pokedexcompose.Screen

internal sealed class Action {
    sealed class Navigation : Action() {
        object Back : Navigation()
        data class To(val screen: Screen) : Navigation()
    }
}
