package com.kgoncharov.mvi_demo.presentation.main

import com.kgoncharov.mvi_demo.data.Task
import com.kgoncharov.mvi_demo.presentation.base.UiState
import javax.annotation.concurrent.Immutable

@Immutable
data class MainScreenState(
    val isLoading: Boolean,
    val data: List<Task>,
    val isAddDialogShowing: Boolean
) : UiState {
    companion object {
        fun initial() = MainScreenState(
            isLoading = true,
            data = emptyList(),
            isAddDialogShowing = false
        )
    }
}
