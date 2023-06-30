package com.example.todo.domain

import com.example.todo.domain.model.TodoItem
import com.example.todo.utils.ResourceState
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface TodoItemsApiRepository{

    suspend fun getTodoListApi(): ResourceState<Any>

    suspend fun addTodoApi(revision:Int,todoItem: TodoItem)

    suspend fun deleteTodoApi(id: String)

    suspend fun updateTodoApi(todoItem: TodoItem)
}