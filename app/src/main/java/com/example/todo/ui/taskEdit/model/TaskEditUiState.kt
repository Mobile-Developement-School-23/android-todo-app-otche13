package com.example.todo.ui.taskEdit.model

import com.example.todo.data.model.Priority
import com.example.todo.utils.tomorrowLocalDate
import java.time.LocalDate

data class TaskEditUiState(
    val description: String = "",
    val priority: Priority = Priority.COMMON,
    val deadline: LocalDate = tomorrowLocalDate,
    val isDeadlineVisible: Boolean = false,
    val isEditing: Boolean = false
) {
    val isDeleteEnabled: Boolean
        get() = description.isNotBlank() || isEditing
}