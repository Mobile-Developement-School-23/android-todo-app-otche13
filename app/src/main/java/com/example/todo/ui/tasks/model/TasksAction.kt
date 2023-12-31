package com.example.todo.ui.tasks.model

import com.example.todo.data.model.TodoItem

sealed class TasksAction {
    object CreateTask: TasksAction()
    data class UpdateTask(val todoItem: TodoItem): TasksAction()
    data class DeleteTask(val todoItem: TodoItem) : TasksAction()
    data class EditTask(val todoItem: TodoItem) : TasksAction()
    data class UpdateDoneVisibility(val visible: Boolean): TasksAction()
    object UpdateRequest: TasksAction()
    object RefreshTasks: TasksAction()
    object SignOut: TasksAction()
}