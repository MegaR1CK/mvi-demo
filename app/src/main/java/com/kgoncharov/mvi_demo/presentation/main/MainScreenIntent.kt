package com.kgoncharov.mvi_demo.presentation.main

import com.kgoncharov.mvi_demo.data.Task
import com.kgoncharov.mvi_demo.presentation.base.UiIntent
import javax.annotation.concurrent.Immutable

@Immutable
sealed class MainScreenIntent : UiIntent {
    data class ShowData(val items: List<Task>) : MainScreenIntent()
    data class OnChangeDialogState(val isShowing: Boolean) : MainScreenIntent()
    data class AddItem(val text: String) : MainScreenIntent()
    data class OnItemCheckedChanged(val index: Int, val isChecked: Boolean) : MainScreenIntent()
    object DismissDialog : MainScreenIntent()
}
