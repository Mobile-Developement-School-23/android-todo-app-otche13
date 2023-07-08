package com.example.todo.ui.tasks

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


internal const val TasksScreenRoutePattern = "tasks"

internal fun NavController.navigateToTasks() {
    this.navigate((TasksScreenRoutePattern)) {
        popUpTo(0)
        launchSingleTop = true
    }
}

internal fun NavGraphBuilder.tasksScreen(
    onNavigateToCreateTask: () -> Unit,
    onNavigateToEditTask: (String) -> Unit,
    onSignOut: () -> Unit
) {
    composable(TasksScreenRoutePattern) {
        val viewModel: TasksViewModel = hiltViewModel()
        val uiState by  viewModel.uiState.collectAsStateWithLifecycle()
        TasksScreen(
            uiState = uiState,
            uiEvent = viewModel.uiEvent,
            onAction = viewModel::onAction,
            onCreateTask = onNavigateToCreateTask,
            onEditTask = onNavigateToEditTask,
            onSignOut = onSignOut
        )
    }
}