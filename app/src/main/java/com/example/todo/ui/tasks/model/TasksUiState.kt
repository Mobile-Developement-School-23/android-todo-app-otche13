package com.example.todo.ui.tasks.model

import com.example.todo.data.model.TodoItem

data class TasksUiState(
    val isRefreshing: Boolean = false,
    val doneVisible: Boolean = true,
    val tasks: List<TodoItem> = emptyList()
)
