package com.kgoncharov.mvi_demo.presentation.main

import com.kgoncharov.mvi_demo.data.Task
import com.kgoncharov.mvi_demo.presentation.base.UiEvent
import javax.annotation.concurrent.Immutable

@Immutable
sealed class MainScreenEvent : UiEvent {
    data class ShowData(val items: List<Task>) : MainScreenEvent()
    data class OnChangeDialogState(val isShowing: Boolean) : MainScreenEvent()
    data class AddItem(val text: String) : MainScreenEvent()
    data class OnItemCheckedChanged(val index: Int, val isChecked: Boolean) : MainScreenEvent()
    object DismissDialog : MainScreenEvent()
}
