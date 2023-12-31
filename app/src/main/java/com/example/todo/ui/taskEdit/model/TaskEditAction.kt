package com.example.todo.ui.taskEdit.model

import com.example.todo.data.model.Priority

sealed class TaskEditAction {
    data class DescriptionChange(val description: String) : TaskEditAction()
    data class UpdateDeadlineVisibility(val visible: Boolean): TaskEditAction()
    data class UpdatePriority(val priority: Priority) : TaskEditAction()
    data class UpdateDeadline(val deadline: Long) : TaskEditAction()

    object SaveTask: TaskEditAction()
    object NavigateUp: TaskEditAction()
    object DeleteTask : TaskEditAction()
}
