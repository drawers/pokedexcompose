package com.tsongkha.pokedexcompose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tsongkha.pokedexcompose.ui.grid.Fake
import com.tsongkha.pokedexcompose.ui.grid.GridScreen
import com.tsongkha.pokedexcompose.ui.theme.PokedexComposeTheme

@Composable
internal fun App(appState: AppState) {
    PokedexComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            GridScreen(pokemons = Fake.pokemons)
        }
    }
}