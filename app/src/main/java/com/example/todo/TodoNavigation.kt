package com.example.todo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.todo.data.abstraction.AuthInfoProvider
import com.example.todo.ui.auth.AuthScreenRoutePattern
import com.example.todo.ui.auth.authScreen
import com.example.todo.ui.auth.navigateToAuth
import com.example.todo.ui.taskEdit.navigateToTaskEdit
import com.example.todo.ui.taskEdit.taskEditScreen
import com.example.todo.ui.tasks.TasksScreenRoutePattern
import com.example.todo.ui.tasks.navigateToTasks
import com.example.todo.ui.tasks.tasksScreen
import com.example.todo.ui.theme.ExtendedTheme

@Composable
fun TodoNavigation(authProvider: AuthInfoProvider) {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ExtendedTheme.colors.backPrimary
    ) {
        NavHost(
            navController = navController,
            startDestination = if (authProvider.authInfo().token.isBlank()) AuthScreenRoutePattern else TasksScreenRoutePattern
        ) {
            authScreen(
                onSuccessAuth = navController::navigateToTasks
            )
            tasksScreen(
                onNavigateToCreateTask = navController::navigateToTaskEdit,
                onNavigateToEditTask = navController::navigateToTaskEdit,
                onSignOut = navController::navigateToAuth
            )
            taskEditScreen(
                onNavigateUp = navController::navigateUp,
                onSuccessSave = navController::navigateUp
            )
        }
    }
}