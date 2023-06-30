package com.example.todo.domain

import com.example.todo.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoItemsRepository {
    suspend fun getTodoItems(): Flow<List<TodoItem>>
    suspend fun findItemById(id: String): TodoItem?
    suspend fun addTodoItem(task: TodoItem)
    suspend fun updateTodoItem(task: TodoItem)
    suspend fun deleteTodoItem(task: TodoItem)
    suspend fun updateDoneTodoItemsVisibility(visible: Boolean)
    fun doneVisible(): Flow<Boolean>
}