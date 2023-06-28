package com.example.todo.di

import android.app.Application
import androidx.room.Room
import com.example.todo.data.TodoItemsRepositoryImpl
import com.example.todo.domain.TodoItemsRepository
import com.example.todo.domain.LocalTasksRepository
import com.plcoding.cleanarchitecturenoteapp.feature_note.data.data_source.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoDatabase {
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            TodoDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(db: TodoDatabase): TodoItemsRepository {
        return TodoItemsRepositoryImpl(db.todoDao)
    }
}