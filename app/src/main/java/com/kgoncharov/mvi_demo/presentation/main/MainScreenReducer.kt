package com.kgoncharov.mvi_demo.presentation.main

import com.kgoncharov.mvi_demo.presentation.base.Reducer
import com.kgoncharov.mvi_demo.presentation.main.taskslist.TasksListItem
import javax.inject.Inject

class MainScreenReducer @Inject constructor(
    private val store: MainScreenStore
) : Reducer<MainScreenState, MainScreenIntent> {

    override suspend fun reduce(oldState: MainScreenState, intent: MainScreenIntent) = when (intent) {
        is MainScreenIntent.ShowData -> oldState.copy(isLoading = false, data = intent.items)
        is MainScreenIntent.OnChangeDialogState -> oldState.copy(isAddDialogShowing = intent.isShowing)
        is MainScreenIntent.DismissDialog -> oldState.copy(isAddDialogShowing = false)
        is MainScreenIntent.AddItem -> {
            val list = oldState.data.toMutableList().apply {
                add(
                    index = size - 1,
                    element = TasksListItem.TaskItem(text = intent.text, isCompleted = false)
                )
            }
            oldState.copy(data = list, isAddDialogShowing = false)
        }
        is MainScreenIntent.OnItemCheckedChanged -> {
            val newList = oldState.data.toMutableList()
            (newList[intent.index] as? TasksListItem.TaskItem)?.let { item ->
                newList[intent.index] = item.copy(isCompleted = intent.isChecked)
            }
            store.effect(MainScreenEffect.ShowToast("position: ${intent.index}, isCompleted: ${intent.isChecked}"))
            oldState.copy(data = newList)
        }
    }
}
