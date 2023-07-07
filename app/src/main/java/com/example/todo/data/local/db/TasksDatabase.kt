package com.example.todo.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todo.data.model.TodoItem
import com.example.todo.data.model.converters.LocalDateConverter
import com.example.todo.data.model.converters.LocalDateTimeConverter

@Database(entities = [TodoItem::class], version = 1)
@TypeConverters(LocalDateConverter::class, LocalDateTimeConverter::class)
abstract class TasksDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
}