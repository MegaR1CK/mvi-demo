package com.kgoncharov.mvi_demo.domain

import com.kgoncharov.mvi_demo.data.TasksRepository
import com.kgoncharov.mvi_demo.data.Task
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val tasksRepository: TasksRepository
) {
    suspend fun execute(): List<Task> {
        return tasksRepository.getTasks()
    }
}
