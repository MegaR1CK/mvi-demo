package com.kgoncharov.mvi_demo.presentation.main

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.kgoncharov.mvi_demo.domain.GetTasksUseCase
import com.kgoncharov.mvi_demo.presentation.main.taskslist.TaskListMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val mainScreenStore: MainScreenStore,
    private val taskListMapper: TaskListMapper
) : ViewModel() {

    private var effectsCollectJob: Job? = null

    val stateFlow: StateFlow<MainScreenState>
        get() = mainScreenStore.stateFlow

    init {
        viewModelScope.launch {
            sendIntent(
                MainScreenIntent.ShowData(taskListMapper.buildList(getTasksUseCase.execute()))
            )
        }
    }

    fun bindEffects(lifecycleOwner: LifecycleOwner, context: Context) {
        effectsCollectJob = lifecycleOwner.lifecycleScope.launch {
            mainScreenStore.effectsFlow.collect { effect ->
                when (effect) {
                    is MainScreenEffect.ShowToast ->
                        Toast.makeText(context, effect.text, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun addItem(text: String) = sendIntent(MainScreenIntent.AddItem(text))

    fun changeAddDialogState(isVisible: Boolean) = sendIntent(MainScreenIntent.OnChangeDialogState(isVisible))

    fun onItemCheckedChanged(index: Int, isChecked: Boolean) = sendIntent(MainScreenIntent.OnItemCheckedChanged(index, isChecked))

    override fun onCleared() {
        effectsCollectJob = null
        super.onCleared()
    }

    private fun sendIntent(intent: MainScreenIntent) = viewModelScope.launch {
        mainScreenStore.dispatch(intent)
    }
}
