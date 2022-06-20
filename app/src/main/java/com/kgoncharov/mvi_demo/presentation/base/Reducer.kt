package com.kgoncharov.mvi_demo.presentation.base

/**
 * Reducer отвечает за преобразование старого стейта и события в новый стейт
 */
interface Reducer<S : UiState, I : UiIntent> {
    suspend fun reduce(oldState: S, intent: I): S
}
