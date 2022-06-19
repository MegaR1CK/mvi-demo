package com.kgoncharov.mvi_demo.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

/**
 * ViewModel отвечает за обработку действий пользователя
 * и отправку Event в Reducer, а также за запросы данных
 */
abstract class BaseViewModel<S : UiState, in E : UiEvent> : ViewModel() {
    abstract val state: Flow<S>
}
