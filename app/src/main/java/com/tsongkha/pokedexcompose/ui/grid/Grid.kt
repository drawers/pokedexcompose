package com.tsongkha.pokedexcompose.ui.grid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tsongkha.pokedexcompose.R
import com.tsongkha.pokedexcompose.ui.common.PokedexAppBar
import com.tsongkha.pokedexcompose.ui.theme.PokedexComposeTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun PokemonGrid(
    pokemons: ImmutableList<PokemonViewState>,
    paddingValues: PaddingValues,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2),
        modifier = Modifier.padding(
            start = paddingValues.calculateStartPadding(LocalLayoutDirection.current) + 8.dp,
            end = paddingValues.calculateEndPadding(LocalLayoutDirection.current) + 8.dp,
            top = paddingValues.calculateTopPadding() + 12.dp,
            bottom = paddingValues.calculateBottomPadding()
        )
    ) {
        items(pokemons, itemContent = { PokemonCard(pokemon = it) })
    }
}

@Composable
private fun PokemonCard(
    pokemon: PokemonViewState,
) {
    var paletteColor: Int? by remember { mutableStateOf(null) }

    Card(
        colors = pokemonCardColors(paletteColor),
        modifier = Modifier
            .aspectRatio(1.0f, false)
            .padding(start = 8.dp, end = 8.dp, bottom = 12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxHeight(0.75f)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .allowHardware(false)
                    .data(pokemon.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = pokemon.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 16.dp),
                onSuccess = { success ->
                    Palette.Builder(success.result.drawable.toBitmap()).generate {
                        paletteColor = it?.dominantSwatch?.rgb
                    }
                }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text = pokemon.name,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
private fun pokemonCardColors(containerColor: Int?): CardColors {
    return if (containerColor == null) {
        CardDefaults.cardColors()
    } else {
        CardDefaults.cardColors(containerColor = Color(containerColor))
    }
}

@Preview(showBackground = true)
@Composable
private fun PokemonGridPreview() {
    PokedexComposeTheme {
        Scaffold(topBar = { PokedexAppBar(stringResource(R.string.pokemon)) }) {
            PokemonGrid(paddingValues = it, pokemons = Fake.pokemons.toImmutableList())
        }
    }
}


