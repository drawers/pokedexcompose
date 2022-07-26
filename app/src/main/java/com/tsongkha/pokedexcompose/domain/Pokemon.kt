package com.tsongkha.pokedexcompose.domain

import java.util.*

internal data class Pokemon(
    val id: String,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<Type>,
    val baseStats: BaseStats,
) {
    internal enum class Type {
        FIGHTING,
        FLYING,
        POISON,
        GROUND,
        ROCK,
        BUG,
        GHOST,
        STEEL,
        FIRE,
        WATER,
        GRASS,
        ELECTRIC,
        PSYCHIC,
        ICE,
        DRAGON,
        FAIRY,
        DARK,
        UNKNOWN;
    }

    internal class BaseStats private constructor(
        private val stats: Map<Stat, Int>
    ) {

        operator fun get(stat: Stat): Int {
            return checkNotNull(stats[stat]) {
                "A value must be present due to construction only being possible through factory function"
            }
        }

        fun displayString(stat: Stat): String {
            return "${get(stat)}/${stat.max}"
        }

        companion object {

            fun create(
                hp: Int,
                atk: Int,
                def: Int,
                spd: Int,
                exp: Int
            ): BaseStats = BaseStats(
                EnumMap(
                    mapOf(
                        Stat.HP to hp,
                        Stat.ATK to atk,
                        Stat.DEF to def,
                        Stat.SPD to spd,
                        Stat.EXP to exp,
                    )
                )
            )
        }
    }

    internal enum class Stat(val max: Int) {
        HP(300),
        ATK(300),
        DEF(300),
        SPD(300),
        EXP(1000);
    }
}

