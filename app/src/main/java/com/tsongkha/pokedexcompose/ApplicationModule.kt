package com.tsongkha.pokedexcompose

import kotlinx.coroutines.CoroutineScope
import org.koin.dsl.module

fun applicationModule(applicationScope: CoroutineScope) = module {
    single { applicationScope }
}
