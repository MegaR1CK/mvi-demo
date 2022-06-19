package com.kgoncharov.mvi_demo.presentation.base

import com.kgoncharov.mvi_demo.BuildConfig
import com.kgoncharov.mvi_demo.presentation.utils.TimeCapsule
import com.kgoncharov.mvi_demo.presentation.utils.TimeTravelCapsule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Reducer отвечает за хранение стейта и
 * преобразование старого стейта и события в новый стейт
 */
abstract class BaseReducer<S : UiState, E : UiEvent>(initialState: S) {

    private val _state: MutableStateFlow<S> = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state

    val timeCapsule: TimeCapsule<S> = TimeTravelCapsule { storedState ->
        _state.tryEmit(storedState)
    }

    init {
        timeCapsule.addState(initialState)
    }

    fun sendEvent(event: E) {
        reduce(_state.value, event)
    }

    fun setState(newState: S) {
        val isSuccess = _state.tryEmit(newState)
        if (BuildConfig.DEBUG && isSuccess) timeCapsule.addState(newState)
    }

    abstract fun reduce(oldState: S, event: E)
}

interface UiState

interface UiEvent
