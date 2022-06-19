package com.kgoncharov.mvi_demo.presentation.main

import com.kgoncharov.mvi_demo.data.Task
import com.kgoncharov.mvi_demo.presentation.base.BaseReducer

class MainScreenReducer constructor(
    initial: MainScreenState
) : BaseReducer<MainScreenState, MainScreenEvent>(initial) {

    override fun reduce(oldState: MainScreenState, event: MainScreenEvent) = setState(
        when (event) {
            is MainScreenEvent.ShowData -> oldState.copy(isLoading = false, data = event.items)
            is MainScreenEvent.OnChangeDialogState -> oldState.copy(isAddDialogShowing = event.isShowing)
            is MainScreenEvent.DismissDialog -> oldState.copy(isAddDialogShowing = false)
            is MainScreenEvent.AddItem -> {
                val list = oldState.data.toMutableList().apply {
                    add(Task(text = event.text, isCompleted = false))
                }
                oldState.copy(data = list, isAddDialogShowing = false)
            }
            is MainScreenEvent.OnItemCheckedChanged -> {
                val newList = oldState.data.toMutableList()
                newList[event.index] = newList[event.index].copy(isCompleted = event.isChecked)
                oldState.copy(data = newList)
            }
        }
    )
}