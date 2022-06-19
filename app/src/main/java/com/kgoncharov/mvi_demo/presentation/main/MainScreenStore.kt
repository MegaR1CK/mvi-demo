package com.kgoncharov.mvi_demo.presentation.main

import com.kgoncharov.mvi_demo.presentation.base.Store
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Provider

class MainScreenStore @Inject constructor(
    private val reducer: Provider<MainScreenReducer>,
) : Store<MainScreenState, MainScreenEffect, MainScreenEvent> {

    private val _effectsFlow = Channel<MainScreenEffect>(Channel.BUFFERED)
    override val effectsFlow: Flow<MainScreenEffect> = _effectsFlow.receiveAsFlow()

    private val _stateFlow = MutableStateFlow(MainScreenState.initial())
    override val stateFlow: StateFlow<MainScreenState> = _stateFlow

    override suspend fun dispatch(event: MainScreenEvent) {
        _stateFlow.tryEmit(reducer.get().reduce(_stateFlow.value, event))
    }

    override suspend fun effect(effect: MainScreenEffect) {
        _effectsFlow.send(effect)
    }
}
