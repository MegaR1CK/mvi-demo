package com.kgoncharov.mvi_demo.presentation.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/** Store отвечает за хранение потоков стейтов и эффектов */
interface Store<S : UiState, EF : UiEffect, EV : UiEvent> {
    val stateFlow: StateFlow<S>
    val effectsFlow: Flow<EF>
    suspend fun dispatch(event: EV)
    suspend fun effect(effect: EF)
}
