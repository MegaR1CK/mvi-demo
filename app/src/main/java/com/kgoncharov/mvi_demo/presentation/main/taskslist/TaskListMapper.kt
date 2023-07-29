package com.kgoncharov.mvi_demo.presentation.main.taskslist

import com.kgoncharov.mvi_demo.data.Task
import javax.inject.Inject

class TaskListMapper @Inject constructor() {
    fun buildList(tasks: List<Task>): List<TasksListItem> {
        val tasksList = mutableListOf<TasksListItem>()
        tasksList.addAll(tasks.map { task -> TasksListItem.TaskItem(task.text, task.isCompleted) })
        tasksList.add(TasksListItem.AddTasksItem)
        return tasksList
    }
}
