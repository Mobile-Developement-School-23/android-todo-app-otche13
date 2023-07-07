package com.example.todo.data.remote.model

import com.example.todo.data.model.TodoItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodoItemsResponse(
    @SerialName("list") val tasks: List<TodoItem>,
    @SerialName("revision") val revision: String,
    @SerialName("status") val status: String
)