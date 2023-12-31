package com.example.todo.data.remote.model

import com.example.todo.data.model.TodoItemDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodoItemsRequest(
    @SerialName("list") val tasks: List<TodoItemDto>
)