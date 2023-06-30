package com.example.todo.data.api

import com.example.todo.domain.model.TodoItem
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.util.Date

data class TodoApiResponseList(
    @SerializedName("status") val status: String,
    @SerializedName("revision") val revision: Int,
    @SerializedName("list") val list: List<TodoItem>
)

data class TodoApiRequestList(
    @SerializedName("status") val status: String,
    @SerializedName("list") val list: List<TodoItem>
)

data class TodoApiResponseElement(
    @SerializedName("revision") val revision: Int,
    @SerializedName("status") val status: String,
    @SerializedName("element") val element: TodoItem
)

data class TodoApiRequestElement(
    @SerializedName("element") val element: TodoItem
)