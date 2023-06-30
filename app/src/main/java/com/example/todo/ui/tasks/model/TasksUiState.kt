package com.example.todo.ui.tasks.model

import com.example.todo.domain.model.TodoItem

data class TasksUiState(
    val doneVisible: Boolean = true,
    val tasks: List<TodoItem> = emptyList()
)
