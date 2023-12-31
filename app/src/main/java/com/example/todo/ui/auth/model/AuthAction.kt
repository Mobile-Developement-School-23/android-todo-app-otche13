package com.example.todo.ui.auth.model

sealed class AuthAction {
    object AuthClick: AuthAction()
    data class AuthResult(val token: String?): AuthAction()
}