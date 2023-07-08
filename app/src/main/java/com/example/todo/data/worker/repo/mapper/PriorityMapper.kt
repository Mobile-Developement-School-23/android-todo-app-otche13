package com.example.todo.data.repo.mapper

import com.example.todo.data.model.Priority

fun Priority.mapToString() = when(this) {
    Priority.COMMON -> "basic"
    Priority.LOW -> "low"
    Priority.HIGH -> "important"
}

fun priorityFromString(str: String) = when(str) {
    "important" -> Priority.HIGH
    "low" -> Priority.LOW
    else -> Priority.COMMON
}
