package com.kgoncharov.mvi_demo.presentation.main

import com.kgoncharov.mvi_demo.presentation.base.UiEffect
import com.kgoncharov.mvi_demo.presentation.base.UiIntent
import com.kgoncharov.mvi_demo.presentation.main.taskslist.TasksListItem
import javax.annotation.concurrent.Immutable

@Immutable
sealed class MainScreenIntent : UiIntent {
    data class ShowData(val items: List<TasksListItem>) : MainScreenIntent()
    data class OnChangeDialogState(val isShowing: Boolean) : MainScreenIntent()
    data class AddItem(val text: String) : MainScreenIntent()
    data class OnItemCheckedChanged(val index: Int, val isChecked: Boolean) : MainScreenIntent()
    object DismissDialog : MainScreenIntent()
}

@Immutable
sealed class MainScreenEffect : UiEffect {
    data class ShowToast(val text: String) : MainScreenEffect()
}
