package com.kgoncharov.mvi_demo.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.AlertDialog
import androidx.compose.material.Checkbox
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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
import androidx.compose.ui.unit.dp
import com.kgoncharov.mvi_demo.data.Task

@Composable
fun MainScreen(viewModel: MainViewModel, paddingValues: PaddingValues) {
    val state by viewModel.stateFlow.collectAsState()
    viewModel.bindEffects(LocalLifecycleOwner.current, LocalContext.current)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        if (state.isLoading) {
            LoadingIndicator()
        } else {
            TasksList(
                tasksList = state.data,
                onCheckedChanged = { index, isChecked -> viewModel.onItemCheckedChanged(index, isChecked) },
                onAddButtonClick = { viewModel.changeAddDialogState(isVisible = true) },
                isDialogShowing = state.isAddDialogShowing,
                onDialogSubmit = viewModel::addItem,
                onDialogDismiss = { viewModel.changeAddDialogState(isVisible = false) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TasksList(
    @PreviewParameter(TaskListPreviewParameterProvider::class) tasksList: List<Task>,
    onAddButtonClick: () -> Unit = { },
    onCheckedChanged: (Int, Boolean) -> Unit = { _, _ -> },
    isDialogShowing: Boolean = false,
    onDialogSubmit: (String) -> Unit = { },
    onDialogDismiss: () -> Unit = { }
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            content = {
                itemsIndexed(tasksList) { index, task ->
                    TaskItem(task, onCheckedChanged, index)
                }
            }
        )
        FloatingActionButton(
            onClick = onAddButtonClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add task")
        }
        if (isDialogShowing) AddItemDialog(onDialogSubmit, onDialogDismiss)
    }

}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview(name = "TaskItem", showBackground = true)
@Composable
fun TaskItem(
    @PreviewParameter(TaskPreviewParameterProvider::class) task: Task,
    onCheckedChanged: (Int, Boolean) -> Unit = { _, _ -> },
    index: Int = 0
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = { isChecked -> onCheckedChanged.invoke(index, isChecked) }
        )
        Text(text = task.text)
    }
}

@Preview
@Composable
fun AddItemDialog(
    onDialogSubmit: (String) -> Unit = { },
    onDialogDismiss: () -> Unit = { }
) {
    var text by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = { onDialogDismiss() },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(text = "Enter task text") }
            )
        },
        confirmButton = {
            TextButton(onClick = { onDialogSubmit(text) }) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDialogDismiss) {
                Text(text = "Cancel")
            }
        }
    )
}


class TaskPreviewParameterProvider : PreviewParameterProvider<Task> {
    override val values = sequenceOf(
        Task(text = "Task 1", isCompleted = false)
    )
}

class TaskListPreviewParameterProvider : PreviewParameterProvider<List<Task>> {
    override val values = sequenceOf(
        listOf(
            Task(text = "Task 1", isCompleted = false),
            Task(text = "Task 2", isCompleted = true),
            Task(text = "Task 3", isCompleted = false)
        )
    )
}
