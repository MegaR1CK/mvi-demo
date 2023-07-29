package com.kgoncharov.mvi_demo.presentation.main.taskslist

sealed class TasksListItem {
    object AddTasksItem : TasksListItem()
    data class TaskItem(
        val text: String,
        val isCompleted: Boolean
    ) : TasksListItem()
}
