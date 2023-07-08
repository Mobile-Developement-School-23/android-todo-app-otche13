package com.example.todo.ui.taskEdit

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

internal const val TaskId = "taskId"
private const val TaskEdit = "taskEdit"
internal const val TaskEditScreenRoutePattern = "$TaskEdit?$TaskId={$TaskId}"

internal fun NavController.navigateToTaskEdit(id: String = "") {
    this.navigate(
        route = if (id.isNotBlank()) "$TaskEdit?$TaskId=$id"
            else TaskEdit
    ) {
        launchSingleTop = true
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
internal fun NavGraphBuilder.taskEditScreen(
    onNavigateUp: () -> Unit,
    onSuccessSave: () -> Unit
) {
    composable(
        route = TaskEditScreenRoutePattern,
        arguments = listOf(navArgument(TaskEdit) {
            nullable = true
        }
        )
    ) {
        val viewModel: TaskEditViewModel = hiltViewModel()
        val uiState by  viewModel.uiState.collectAsStateWithLifecycle()

        TaskEditScreen(
            uiState = uiState,
            uiEvent = viewModel.uiEvent,
            onAction = viewModel::onAction,
            onNavigateUp = onNavigateUp,
            onSave = onSuccessSave
        )
    }
}