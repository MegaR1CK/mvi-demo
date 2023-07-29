package com.kgoncharov.mvi_demo.presentation.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.sp
import com.kgoncharov.mvi_demo.presentation.main.taskslist.TasksListItem

@Composable
fun MainScreen(viewModel: MainViewModel) {
    viewModel.bindEffects(LocalLifecycleOwner.current, LocalContext.current)
    val screenState by viewModel.stateFlow.collectAsState()
    if (screenState.isLoading) {
        LoadingScreen()
    } else {
        MainScreenContent(
            items = screenState.data,
            isAddDialogShowing = screenState.isAddDialogShowing,
            onAddItemClick = { viewModel.changeAddDialogState(isVisible = true) },
            onItemCheckedChanged = { index, isChecked ->
                viewModel.onItemCheckedChanged(index, isChecked)
            },
            onDialogOkClick = viewModel::addItem,
            onDialogDismissClick = { viewModel.changeAddDialogState(isVisible = false) }
        )
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun MainScreenContent(
    items: List<TasksListItem>,
    isAddDialogShowing: Boolean,
    onAddItemClick: () -> Unit,
    onItemCheckedChanged: (Int, Boolean) -> Unit,
    onDialogOkClick: (String) -> Unit,
    onDialogDismissClick: () -> Unit
) {
    Box {
        LazyColumn(content = {
            itemsIndexed(items) { index, item ->
                when (item) {
                    is TasksListItem.TaskItem ->
                        TasksListItem(task = item, onCheckedChange = onItemCheckedChanged, index)
                    is TasksListItem.AddTasksItem ->
                        AddTaskListItem(onAddItemClick)
                }
            }
        })
        if (isAddDialogShowing) AddTaskDialog(onDialogOkClick, onDialogDismissClick)
    }
}

@Composable
fun AddTaskDialog(
    onDialogOkClick: (String) -> Unit,
    onDialogDismissClick: () -> Unit
) {
    var text by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = { },
        text = {
           TextField(
               value = text,
               onValueChange = { newText -> text = newText }
           )
        },
        confirmButton = {
            Button(
                onClick = { onDialogOkClick(text) }
            ) {
                Text(text = "Ok")
            }
        },
        dismissButton = {
            Button(
                onClick = { onDialogDismissClick() }
            ) {
                Text(text = "Cancel")
            }
        }
    )
}

@Composable
fun TasksListItem(task: TasksListItem.TaskItem, onCheckedChange: (Int, Boolean) -> Unit, index: Int) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onCheckedChange(index, !task.isCompleted) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = { isChecked -> onCheckedChange(index, isChecked) }
        )
        Text(
            text = task.text,
            fontSize = 20.sp
        )
    }
}

@Composable
fun AddTaskListItem(onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        IconButton(onClick = onClick) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = null)
        }
    }
}

class TaskParameterProvider(
    override val values: Sequence<TasksListItem.TaskItem> =
        sequenceOf(TasksListItem.TaskItem(text = "Task", isCompleted = true))
) : PreviewParameterProvider<TasksListItem.TaskItem>

@Composable
@Preview(showBackground = true)
fun TaskListItemPreview(
    @PreviewParameter(TaskParameterProvider::class) task: TasksListItem.TaskItem
) = TasksListItem(task, { _, _ -> }, 1)

@Composable
@Preview(showBackground = true)
fun TaskAddListItemPreview() = AddTaskListItem { }
