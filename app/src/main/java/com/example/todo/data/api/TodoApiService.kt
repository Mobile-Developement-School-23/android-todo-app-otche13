package com.example.todo.data.api

import com.example.todo.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoApiService {

    @GET("list")
    suspend fun getTodoList(): Response<TodoApiResponseList>

    @GET("list/{id}")
    suspend fun getTodoById(
        @Path("id") id:String
    ): Response<TodoApiResponseElement>

    @POST("list")
    suspend fun addTodoItem(
        @Header("X-Last-Known-Revision") revision:Int,
        @Body todoItem: TodoItem
    ): Response<TodoApiResponseElement>

    @DELETE("list/{id}")
    suspend fun deleteTodoItem(
        @Header("X-Last-Known-Revision") revision: String,
        @Path("id") id:String
    ): Response<TodoApiResponseElement>

    @PUT("list/{id}")
    suspend fun updateTodoItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Path("id") id:String,
        @Body todoItem: TodoApiRequestElement
    ): Response<TodoApiResponseElement>

    @PATCH ("list")
    suspend fun updateTodoList(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body todoItem: TodoApiRequestList,
    ): Response<TodoApiResponseList>

}