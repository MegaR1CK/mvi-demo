package com.kgoncharov.mvi_demo.presentation.base

/** Screen отвечает за рендер пользовательского интерфейса на основе [UiState] */
interface Screen<S: UiState> {
    fun render(state: S)
}
