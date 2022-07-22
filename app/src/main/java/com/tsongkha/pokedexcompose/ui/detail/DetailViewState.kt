package com.tsongkha.pokedexcompose.ui.detail

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal data class DetailViewState(
    val name: String,
    val types: ImmutableList<Type>,
    val height: Stat,
    val weight: Stat,
) {

    @Immutable
    data class Stat(
        val stat: String,
        val suffix: String,
        val caption: String,
    )

    @Immutable
    enum class Type(val color: Color) {
        FIGHTING(Color(0xFF9F422A)),
        FLYING(Color(0xFF90B1C5)),
        POISON(Color(0xFF642785)),
        GROUND(Color(0xFFAD7235)),
        ROCK(Color(0xFF4B190E)),
        BUG(Color(0xFF179A55)),
        GHOST(Color(0xFF363069)),
        STEEL(Color(0xFF5C756D)),
        FIRE(Color(0xFFB22328)),
        WATER(Color(0xFF2648DC)),
        GRASS(Color(0xFF007C42)),
        ELECTRIC(Color(0xFFE0E64B)),
        PSYCHIC(Color(0xFFAC296B)),
        ICE(Color(0xFF7ECFF2)),
        DRAGON(Color(0xFF378A94)),
        FAIRY(Color(0xFF9E1A44)),
        DARK(Color(0xFF040706)),
        UNKNOWN(Color(0xFF040706));

        val displayName = name.lowercase()
    }
}
