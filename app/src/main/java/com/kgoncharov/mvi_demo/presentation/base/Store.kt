package com.kgoncharov.mvi_demo.presentation.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/** Store отвечает за хранение потоков стейтов и эффектов */
interface Store<S : UiState, E : UiEffect, I : UiIntent> {
    val stateFlow: StateFlow<S>
    val effectsFlow: Flow<E>
    suspend fun dispatch(intent: I)
    suspend fun effect(effect: E)
}
