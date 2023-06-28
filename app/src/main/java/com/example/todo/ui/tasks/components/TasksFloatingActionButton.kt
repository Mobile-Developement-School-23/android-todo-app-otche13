package com.example.todo.ui.tasks.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.example.todo.ui.tasks.model.TasksAction
import com.example.todo.ui.theme.Blue
import com.example.todo.ui.theme.White

@Composable
fun TasksFloatingActionButton(
    onAction: (TasksAction) -> Unit
) {
    FloatingActionButton(
        onClick = { onAction(TasksAction.CreateTask) },
        containerColor = Blue,
        contentColor = White
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null
        )
    }
}