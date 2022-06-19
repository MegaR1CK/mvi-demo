package com.kgoncharov.mvi_demo.presentation.main

import com.kgoncharov.mvi_demo.presentation.base.UiEffect

sealed class MainScreenEffect : UiEffect {
    data class ShowToast(val text: String) : MainScreenEffect()
}
