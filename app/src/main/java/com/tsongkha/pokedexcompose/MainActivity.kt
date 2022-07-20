package com.tsongkha.pokedexcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tsongkha.pokedexcompose.ui.common.PokedexAppBar
import com.tsongkha.pokedexcompose.ui.grid.Fake
import com.tsongkha.pokedexcompose.ui.grid.PokemonGrid
import com.tsongkha.pokedexcompose.ui.theme.PokedexComposeTheme
import kotlinx.collections.immutable.toImmutableList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(topBar = { PokedexAppBar(stringResource(R.string.pokemon)) }) {
                        PokemonGrid(paddingValues = it, pokemons = Fake.pokemons.toImmutableList())
                    }
                }
            }
        }
    }
}

