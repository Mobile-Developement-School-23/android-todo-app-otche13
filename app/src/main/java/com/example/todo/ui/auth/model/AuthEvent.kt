package com.example.todo.ui.auth.model

sealed class AuthEvent {
    object LaunchAuth: AuthEvent()
    object AuthSuccess: AuthEvent()
}