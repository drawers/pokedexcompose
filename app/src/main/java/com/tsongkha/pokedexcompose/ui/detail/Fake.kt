package com.tsongkha.pokedexcompose.ui.detail

import kotlinx.collections.immutable.persistentListOf

internal object Fake {
    val fake = DetailViewState(
        name = "bulbasaur",
        types = persistentListOf(DetailViewState.Type.GRASS, DetailViewState.Type.POISON),
        height = DetailViewState.Stat("6.9", "kg", "Weight"),
        weight = DetailViewState.Stat("0.7", "M", "Height"),
    )
}