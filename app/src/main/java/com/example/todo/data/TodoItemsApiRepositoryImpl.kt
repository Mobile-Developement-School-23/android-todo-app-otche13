package com.example.todo.data

import android.annotation.SuppressLint
import android.util.Log
import androidx.core.content.PackageManagerCompat.LOG_TAG
import com.example.todo.data.api.TodoApiRequestElement
import com.example.todo.data.api.TodoApiRequestList
import com.example.todo.data.api.TodoApiService
import com.example.todo.data.db.TodoDatabase
import com.example.todo.domain.TodoItemsApiRepository
import com.example.todo.domain.model.TodoItem
import com.example.todo.utils.ResourceState
import java.time.LocalDate

class TodoItemsApiRepositoryImpl (
    private val database: TodoDatabase,
    private val todoService: TodoApiService,
    private val sharedPreferences: SharedPreferencesApp
    ):TodoItemsApiRepository {

    @SuppressLint("RestrictedApi")
    override suspend fun updateTodoApi(todoItem: TodoItem) {
        try {
            val response = todoService.updateTodoItem(
                revision = sharedPreferences.getRevisionId(),
                id = todoItem.id,
                TodoApiRequestElement(
                    TodoItem(id = todoItem.id,
                        description=todoItem.description,
                        priority = todoItem.priority,
                        deadline = todoItem.deadline,
                        isDone = todoItem.isDone,
                        createdAt = todoItem.createdAt,
                        editedAt = todoItem.editedAt,
                        lastUpdatedBy = todoItem.lastUpdatedBy,
                        color = todoItem.color
                    )
                )
            )

            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    sharedPreferences.putRevisionId(responseBody.revision)
                }
            } else {
                response.errorBody()?.close()
            }
        } catch (e: Exception) {
            Log.e(LOG_TAG, e.message.toString())
        }

    }


    @SuppressLint("RestrictedApi")
    override suspend fun deleteTodoApi(id: String) {
        try {
            val response = todoService.deleteTodoItem(
                revision = sharedPreferences.getId(),
                id = id
            )

            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    sharedPreferences.putRevisionId(responseBody.revision)
                }
            } else {
                response.errorBody()?.close()
            }
        } catch (e: Exception) {
            Log.e(LOG_TAG, e.toString())
        }

    }

    private suspend fun updateTodosApi(zipList: List<TodoItem>): ResourceState<Any> {
        try {
            val response = todoService.updateTodoList(
                revision = sharedPreferences.getRevisionId(),
               TodoApiRequestList(status = "ok", zipList)
            )

            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    sharedPreferences.putRevisionId(responseBody.revision)
                    database.todoDao.updateTodoList(responseBody.list.map { it })
                    return ResourceState.Success(responseBody.list)
                }
            } else {
                response.errorBody()?.close()
            }
        } catch (e: Exception) {
            return ResourceState.Error("Merge failed, continue offline.")
        }

        return ResourceState.Error("Merge failed, continue offline.")
    }

    override suspend fun getTodoListApi(): ResourceState<Any>{
        try {
                val apiListResponse = todoService.getTodoList()

                if (apiListResponse.isSuccessful) {
                    val body =  apiListResponse.body()
                    if (body != null) {
                        val revision = body.revision
                        val networkList = body.list
                        val currentList = database.todoDao.getTodoItemsNoFlow().map {
                            it
                        }
                        val zipList = HashMap<String, TodoItem>()

                        for (item in currentList) {
                            zipList[item.id] = item
                        }
                        for (item in networkList) {
                            if (zipList.containsKey(item.id)) {
                                val item1 = zipList[item.id]
                                val editedItem= LocalDate.parse(item.editedAt.toString())
                                val editedItemOne= LocalDate.parse(item1!!.editedAt.toString())
                                if (editedItem > editedItemOne) {
                                    zipList[item.id] = item
                                } else {
                                    zipList[item.id] = item1
                                }
                            } else if (revision != sharedPreferences.getRevisionId()) {
                                zipList[item.id] = item
                            }
                        }

                        return updateTodosApi(zipList.values.toList())
                    }
                }

            } catch (e: Exception) {
                return ResourceState.Error("Merge failed, continue offline.")
            }
            return ResourceState.Error("Merge failed, continue offline.")
        }

    @SuppressLint("RestrictedApi")
    override suspend fun addTodoApi(revision: Int, todoItem: TodoItem) {
        try {
            val response = todoService.addTodoItem(
                revision = sharedPreferences.getRevisionId(),
                TodoItem(id = todoItem.id,
                    description=todoItem.description,
                    priority = todoItem.priority,
                    deadline = todoItem.deadline,
                    isDone = todoItem.isDone,
                    createdAt = todoItem.createdAt,
                    editedAt = todoItem.editedAt,
                    lastUpdatedBy = todoItem.lastUpdatedBy,
                    color = todoItem.color
                )
            )

            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    sharedPreferences.putRevisionId(responseBody.revision)
                }
            } else {
                response.errorBody()?.close()
            }
        } catch (e: Exception) {
            Log.e(LOG_TAG, e.toString())
        }
    }
}