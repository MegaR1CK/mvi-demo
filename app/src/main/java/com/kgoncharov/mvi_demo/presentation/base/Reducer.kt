package com.kgoncharov.mvi_demo.presentation.base

/**
 * Reducer отвечает за преобразование старого стейта и события в новый стейт
 */
interface Reducer<S : UiState, EV : UiEvent> {
    suspend fun reduce(oldState: S, event: EV): S
}
