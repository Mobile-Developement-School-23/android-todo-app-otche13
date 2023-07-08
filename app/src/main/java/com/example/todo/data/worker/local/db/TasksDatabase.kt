package com.example.todo.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todo.data.model.TodoItemDto
import com.example.todo.data.model.converters.LocalDateConverter
import com.example.todo.data.worker.model.converters.LocalDateTimeConverter

@Database(entities = [TodoItemDto::class], version = 1, exportSchema = false)
@TypeConverters(LocalDateConverter::class, LocalDateTimeConverter::class)
abstract class TasksDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
}