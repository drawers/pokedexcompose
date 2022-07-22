package com.tsongkha.pokedexcompose.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tsongkha.pokedexcompose.R
import com.tsongkha.pokedexcompose.ui.theme.PokedexComposeTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun Detail(viewState: DetailViewState) {
    PokedexComposeTheme {
        Scaffold(topBar = {
            DetailAppBar(
                title = stringResource(R.string.pokedex),
                number = viewState.number
            )
        }) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxWidth()
            ) {
                DetailTitle(text = viewState.name)
                Ribbons(
                    viewState.types
                )
                Stats(
                    persistentListOf(
                        viewState.height,
                        viewState.weight,
                    )
                )
                BaseStats()
                StatBar()
            }
        }
    }
}

@Composable
private fun DetailAppBar(title: String, number: String) {
    SmallTopAppBar(
        navigationIcon = {
            IconButton(
                onClick = { },

                ) {
                Icon(
                    modifier = Modifier.fillMaxWidth(),
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = stringResource(id = R.string.back),
                    tint = MaterialTheme.colorScheme.background
                )
            }
        },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = title, fontWeight = FontWeight.Bold)
                Text(text = number, fontWeight = FontWeight.Bold)
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
private fun DetailTitle(
    text: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 8.dp)
    ) {
        Text(
            text = text, fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun Ribbons(types: ImmutableList<DetailViewState.Type>) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        types.forEach {
            Ribbon(it)
        }
    }
}

@Composable
private fun Ribbon(type: DetailViewState.Type) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(percent = 33))
            .background(type.color)
    ) {
        Text(
            type.displayName,
            modifier = Modifier.padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            )
        )
    }
}

@Composable
private fun Stats(stats: ImmutableList<DetailViewState.Stat>) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        for (stat in stats) {
            Column {
                StatBox(stat)
            }
        }
    }
}

@Composable
private fun StatBox(stat: DetailViewState.Stat) {
    Column {
        Text("${stat.stat} ${stat.suffix}", fontWeight = FontWeight.Bold)
        Text(stat.caption, fontWeight = FontWeight.Light)
    }
}

@Composable
private fun BaseStats() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.base_stats),
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
private fun StatBar() {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "HP", modifier = Modifier.padding(start = 32.dp, end = 32.dp))
        RoundedBar()
    }
}

@Composable
private fun RoundedBar() {
    LinearProgressIndicator(
        modifier = Modifier
            .aspectRatio(22f)
            .fillMaxWidth()
            .padding(end = 32.dp),
        trackColor = Color.Cyan,
        color = Color.Magenta,
        progress = 0.75f
    )
}

@Preview
@Composable
private fun DetailPreview() {
    Detail(Fake.fake)
}
