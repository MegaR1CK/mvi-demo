package com.kgoncharov.mvi_demo.presentation.utils

import com.kgoncharov.mvi_demo.presentation.base.UiState

/**
 * TimeCapsule хранит все стейты, в которых
 * когда-либо был экран, что значительно упрощает отладку
 */
interface TimeCapsule<S : UiState> {
    fun addState(state: S)
    fun selectState(position: Int)
    fun getStates(): List<S>
}

class TimeTravelCapsule<S : UiState>(
    private val onStateSelected: (S) -> Unit
) : TimeCapsule<S> {

    private val states = mutableListOf<S>()

    override fun addState(state: S) {
        states.add(state)
    }

    override fun selectState(position: Int) {
        onStateSelected(states[position])
    }

    override fun getStates(): List<S> {
        return states
    }
}
