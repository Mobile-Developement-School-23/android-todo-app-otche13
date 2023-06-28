package com.example.todo.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.todo.ui.taskEdit.navigateToTaskEdit
import com.example.todo.ui.taskEdit.taskEditScreen
import com.example.todo.ui.tasks.TasksScreenRoutePattern
import com.example.todo.ui.tasks.tasksScreen
import com.example.todo.ui.theme.ExtendedTheme

@Composable
fun TodoNavigation() {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ExtendedTheme.colors.backPrimary
    ) {
        NavHost(
            navController = navController,
            startDestination = TasksScreenRoutePattern
        ) {
            tasksScreen(
                onNavigateToCreateTask = navController::navigateToTaskEdit,
                onNavigateToEditTask = navController::navigateToTaskEdit
            )
            taskEditScreen(
                onNavigateUp = navController::navigateUp,
                onSuccessSave = navController::navigateUp
            )
        }
    }
}