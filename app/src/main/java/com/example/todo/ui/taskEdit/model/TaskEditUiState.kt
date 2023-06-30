package com.example.todo.ui.taskEdit.model

import com.example.todo.domain.model.Priority
import com.example.todo.utils.tomorrowLocalDate
import java.time.LocalDate

data class TaskEditUiState(
    val description: String = "",
    val priority: Priority = Priority.NO,
    val deadline: String = tomorrowLocalDate.toString(),
    val isDeadlineVisible: Boolean = false,
    val isEditing: Boolean = false
) {
    val isDeleteEnabled: Boolean
        get() = description.isNotBlank() || isEditing
}