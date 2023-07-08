package com.example.todo.data.abstraction

interface AuthRepository {
    suspend fun signIn(token: String)
    suspend fun signOut()
}