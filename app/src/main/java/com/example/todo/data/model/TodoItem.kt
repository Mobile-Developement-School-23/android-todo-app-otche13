package com.example.todo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
data class TodoItem(

    @PrimaryKey val id: String,
    val description: String,
    val priority: Priority = Priority.COMMON,
    val deadline: String? = null,
    val isDone: Boolean = false,
    val createdAt: String = LocalDateTime.now().toString(),
    val editedAt: String? = null
)
