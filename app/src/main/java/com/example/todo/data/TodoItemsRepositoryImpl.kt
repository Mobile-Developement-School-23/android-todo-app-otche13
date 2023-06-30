package com.example.todo.data

import com.example.todo.data.db.TodoDao
import com.example.todo.domain.model.TodoItem
import com.example.todo.domain.TodoItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TodoItemsRepositoryImpl (private val dao: TodoDao):TodoItemsRepository{

    private var isDoneVisibleFlow = MutableStateFlow(true)

    private val todoFlow = dao.getTodoItems()

    override suspend fun getTodoItems(): Flow<List<TodoItem>> {
        return todoFlow
    }

    override suspend fun findItemById(id: String): TodoItem? {
        return  dao.getTodoById(id = id)
    }

    override suspend fun addTodoItem(task: TodoItem) {
        return dao.addTodo(todoItem = task )
    }

    override suspend fun updateTodoItem(task: TodoItem) {
        return dao.updateTodo(task)
    }

    override suspend fun deleteTodoItem(task: TodoItem) {
        return dao.deleteTodo(todoItem = task )
    }

    override suspend fun updateDoneTodoItemsVisibility(visible: Boolean) {
        isDoneVisibleFlow.update { visible }
    }

    override fun doneVisible(): Flow<Boolean> = isDoneVisibleFlow.asStateFlow()


}