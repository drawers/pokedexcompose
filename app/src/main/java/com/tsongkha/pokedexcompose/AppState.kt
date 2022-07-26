package com.tsongkha.pokedexcompose

import com.tsongkha.pokedexcompose.domain.Pokemon

internal data class AppState(
    val pokemon: List<Pokemon> = emptyList(),
    val navigation: Navigation,
)
