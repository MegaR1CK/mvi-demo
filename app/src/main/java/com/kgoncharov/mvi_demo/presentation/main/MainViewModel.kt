package com.kgoncharov.mvi_demo.presentation.main

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.kgoncharov.mvi_demo.domain.GetTasksUseCase
import com.kgoncharov.mvi_demo.presentation.base.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val mainScreenStore: MainScreenStore
) : ViewModel() {

    init {
        viewModelScope.launch {
            sendEvent(MainScreenEvent.ShowData(getTasksUseCase.execute()))
        }
    }

    fun bindScreen(screen: Screen<MainScreenState>) = viewModelScope.launch {
        mainScreenStore.stateFlow.collect(screen::render)
    }

    fun bindEffects(lifecycleOwner: LifecycleOwner, context: Context) = lifecycleOwner.lifecycleScope.launch {
        mainScreenStore.effectsFlow.collect {
            when (it) {
                is MainScreenEffect.ShowToast -> Toast.makeText(context, it.text, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun addItem(text: String) = sendEvent(MainScreenEvent.AddItem(text))

    fun changeAddDialogState(isVisible: Boolean) = sendEvent(MainScreenEvent.OnChangeDialogState(isVisible))

    fun onItemCheckedChanged(index: Int, isChecked: Boolean) = sendEvent(MainScreenEvent.OnItemCheckedChanged(index, isChecked))

    private fun sendEvent(event: MainScreenEvent) = viewModelScope.launch {
        mainScreenStore.dispatch(event)
    }
}
