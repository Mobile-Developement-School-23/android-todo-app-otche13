package com.example.todo.ui.taskEdit

import android.os.Bundle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.abstraction.TodoItemsRepository
import com.example.todo.data.model.TodoItem
import com.example.todo.ui.taskEdit.model.TaskEditAction
import com.example.todo.ui.taskEdit.model.TaskEditEvent
import com.example.todo.ui.taskEdit.model.TaskEditUiState
import com.example.todo.utils.dateFromLong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TaskEditViewModel @Inject constructor(
    private val repo: TodoItemsRepository
): ViewModel() {
    private var isEditing = false
    private var previousTask: TodoItem? = null

    private val _uiState = MutableStateFlow(TaskEditUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<TaskEditEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: TaskEditAction) {
        when(action) {
            is TaskEditAction.DescriptionChange -> _uiState.update {
                uiState.value.copy(description = action.description) }
            is TaskEditAction.UpdateDeadlineVisibility -> _uiState.update {
                uiState.value.copy(isDeadlineVisible = action.visible) }
            is TaskEditAction.UpdatePriority -> _uiState.update {
                uiState.value.copy(priority = action.priority) }
            is TaskEditAction.UpdateDeadline -> _uiState.update {
                uiState.value.copy(deadline = dateFromLong(action.deadline)) }
            TaskEditAction.SaveTask -> saveTask()
            TaskEditAction.DeleteTask -> deleteTask()
            TaskEditAction.NavigateUp -> viewModelScope.launch {
                _uiEvent.send(TaskEditEvent.NavigateBack) }
        }
    }

    fun setupViewModel(arguments: Bundle?) {
        val taskId: String? = arguments?.getString(TaskId)
        taskId?.let {
            setupTask(taskId)
        }
    }

    private fun saveTask() {
        if (uiState.value.description.isBlank())
            return

        val newTask = if (isEditing)
            previousTask!!.copy(
                description = uiState.value.description,
                priority = uiState.value.priority,
                deadline = if (uiState.value.isDeadlineVisible) uiState.value.deadline else null
            )
        else
            TodoItem(
                description = uiState.value.description,
                priority = uiState.value.priority,
                deadline = if (uiState.value.isDeadlineVisible) uiState.value.deadline else null
            )

        viewModelScope.launch(Dispatchers.IO) {
            if (isEditing) repo.updateTodoItem(newTask)
            else repo.addTodoItem(newTask)
            _uiEvent.send(TaskEditEvent.SaveTask)
        }
    }

    private fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            if (isEditing)
                previousTask?.let { repo.deleteTodoItem(it) }
            _uiEvent.send(TaskEditEvent.NavigateBack)
        }
    }

    private fun setupTask(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.findItemById(id)?.let { item ->
                isEditing = true
                previousTask = item

                withContext(Dispatchers.Main) {
                    _uiState.update { uiState.value.copy(
                        description = item.description,
                        priority = item.priority,
                        deadline = item.deadline ?: uiState.value.deadline,
                        isDeadlineVisible = item.deadline != null,
                        isEditing = true
                    ) }
                }
            }
        }
    }
}