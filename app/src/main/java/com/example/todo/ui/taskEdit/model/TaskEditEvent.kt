package com.example.todo.ui.taskEdit.model

sealed class TaskEditEvent {
    object NavigateBack: TaskEditEvent()
    object SaveTask: TaskEditEvent()
}