package com.tsongkha.pokedexcompose.ui.grid

internal data class PokemonViewState(
    val id: String,
    val url: String,
    val name: String,
) {
    val imageUrl =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
}
