package com.kgoncharov.mvi_demo.presentation.main

import androidx.lifecycle.viewModelScope
import com.kgoncharov.mvi_demo.domain.GetTasksUseCase
import com.kgoncharov.mvi_demo.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase
) : BaseViewModel<MainScreenState, MainScreenEvent>() {

    private val reducer = MainScreenReducer(MainScreenState.initial())

    override val state = reducer.state

    val timeCapsule = reducer.timeCapsule

    init {
        viewModelScope.launch {
            sendEvent(MainScreenEvent.ShowData(getTasksUseCase.execute()))
        }
    }

    fun addItem(text: String) = sendEvent(MainScreenEvent.AddItem(text))

    fun changeAddDialogState(isVisible: Boolean) = sendEvent(MainScreenEvent.OnChangeDialogState(isVisible))

    fun onItemCheckedChanged(index: Int, isChecked: Boolean) = sendEvent(MainScreenEvent.OnItemCheckedChanged(index, isChecked))

    private fun sendEvent(event: MainScreenEvent) = reducer.sendEvent(event)
}
