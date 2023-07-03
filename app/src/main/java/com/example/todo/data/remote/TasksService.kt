package com.example.todo.data.remote

import com.example.todo.data.abstraction.AuthInfoProvider
import com.example.todo.data.model.AuthInfo
import com.example.todo.data.model.TodoItem
import com.example.todo.data.remote.model.RequestResult
import com.example.todo.data.remote.model.TodoItemRequest
import com.example.todo.data.remote.model.TodoItemResponse
import com.example.todo.data.remote.model.TodoItemsRequest
import com.example.todo.data.remote.model.TodoItemsResponse
import com.example.todo.data.remote.model.safeRequest
import com.example.todo.utils.AUTHORIZATION
import com.example.todo.utils.LAST_KNOWN_REVISION
import com.example.todo.utils.OAuth
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.HttpMessageBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class TasksService @Inject constructor(
    private val client: HttpClient,
    authProvider: AuthInfoProvider
) {
    private val auth = authProvider.authInfoFlow().stateIn(
        CoroutineScope(Dispatchers.IO),
        started = SharingStarted.Eagerly,
        AuthInfo()
    )

    suspend fun getTasks(): RequestResult<TodoItemsResponse> =
        client.safeRequest { get { requestHeaders() } }

    suspend fun mergeTasks(tasks: List<TodoItem>): RequestResult<TodoItemsResponse> =
        client.safeRequest { patch { requestHeaders(); setBody(TodoItemsRequest(tasks)) } }

    suspend fun addTask(task: TodoItem): RequestResult<TodoItemResponse> =
        client.safeRequest { post { requestHeaders(); setBody(TodoItemRequest(task)) } }

    suspend fun getTask(id: String): RequestResult<TodoItemResponse> =
        client.safeRequest { get(id) { requestHeaders() } }

    suspend fun updateTask(task: TodoItem): RequestResult<TodoItemResponse> =
        client.safeRequest { put(task.id) { requestHeaders(); setBody(TodoItemRequest(task)) } }

    suspend fun deleteTask(id: String): RequestResult<TodoItemResponse> =
        client.safeRequest { delete(id) { requestHeaders() } }


    private fun HttpMessageBuilder.requestHeaders() {
        headers {
            append(AUTHORIZATION, "$OAuth ${auth.value.token}")
            append(LAST_KNOWN_REVISION, auth.value.revision)
        }
    }
}