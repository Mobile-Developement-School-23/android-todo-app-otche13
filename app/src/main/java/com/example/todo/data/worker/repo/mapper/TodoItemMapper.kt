package com.example.todo.data.repo.mapper

import com.example.todo.data.model.TodoItem
import com.example.todo.data.model.TodoItemDto


fun TodoItem.toDto() = TodoItemDto(
    id = id,
    description = description,
    deadline = deadline?.toTimestamp(),
    priority = priority.mapToString(),
    isDone = isDone,
    color = color,
    createdAt = createdAt.toTimestamp(),
    editedAt = createdAt.toTimestamp(),
    lastUpdatedBy = lastUpdatedBy
)

fun TodoItemDto.fromDto() = TodoItem(
    id = id,
    description = description,
    deadline = localDateFromTimestamp(deadline),
    priority = priorityFromString(priority),
    isDone = isDone,
    color = color,
    createdAt = localDateTimeFromTimestamp(createdAt),
    editedAt = localDateTimeFromTimestamp(editedAt),
    lastUpdatedBy = lastUpdatedBy
)
