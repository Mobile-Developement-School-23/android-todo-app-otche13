package com.example.todo.ui.taskEdit.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.todo.ui.taskEdit.model.TaskEditEvent
import kotlinx.coroutines.flow.Flow

@Composable
fun TaskEditUiEventHandler(
    uiEvent: Flow<TaskEditEvent>,
    onNavigateUp: () -> Unit,
    onSave: () -> Unit
) {
    LaunchedEffect(Unit) {
        uiEvent.collect {
            when(it) {
                TaskEditEvent.NavigateBack -> onNavigateUp()
                TaskEditEvent.SaveTask -> onSave()
            }
        }
    }
}