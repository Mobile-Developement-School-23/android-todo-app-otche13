package com.plcoding.cleanarchitecturenoteapp.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todo.data.db.TodoDao
import com.example.todo.data.model.TodoItem


@Database(
    entities = [TodoItem::class],
    version = 1
)
abstract class TodoDatabase: RoomDatabase() {

    abstract val todoDao: TodoDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}