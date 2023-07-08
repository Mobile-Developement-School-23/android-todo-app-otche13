package com.example.todo.data.remote.model

import com.example.todo.data.model.TodoItemDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodoItemResponse(
    @SerialName("element") val task: TodoItemDto,
    @SerialName("revision") val revision: String,
    @SerialName("status") val status: String
)
