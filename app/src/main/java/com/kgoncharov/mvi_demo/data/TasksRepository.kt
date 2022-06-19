package com.kgoncharov.mvi_demo.data

import kotlinx.coroutines.delay
import javax.inject.Inject

class TasksRepository @Inject constructor() {
    suspend fun getTasks(): List<Task> {
        delay(2000)
        return listOf(
            Task(text = "Task 1", isCompleted = false),
            Task(text = "Task 2", isCompleted = false),
            Task(text = "Task 3", isCompleted = false),
            Task(text = "Task 4", isCompleted = false)
        )
    }
}
