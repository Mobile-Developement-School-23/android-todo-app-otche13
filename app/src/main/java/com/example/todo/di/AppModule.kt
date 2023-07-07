package com.example.todo.di

import android.content.Context
import androidx.room.Room
import com.example.todo.data.abstraction.AuthInfoMutableProvider
import com.example.todo.data.abstraction.AuthInfoProvider
import com.example.todo.data.abstraction.AuthRepository
import com.example.todo.data.abstraction.TodoItemsRepository
import com.example.todo.data.local.db.TaskDao
import com.example.todo.data.local.db.TasksDatabase
import com.example.todo.data.local.store.AuthInfoDataStoreManager
import com.example.todo.data.remote.TasksService
import com.example.todo.data.repo.AuthRepositoryImpl
import com.example.todo.data.repo.TasksRepositoryImpl
import com.example.todo.utils.BASE_URL
import com.example.todo.utils.DATABASE_NAME
import com.example.todo.utils.LIST
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): TasksDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = TasksDatabase::class.java,
            name = DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(
        db: TasksDatabase
    ): TaskDao {
        return db.taskDao()
    }

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
            defaultRequest {
                //header(GENERATE_FAILS, "10")
                contentType(ContentType.Application.Json)
                url(BASE_URL + LIST)
            }
        }
    }

    @Provides
    @Singleton
    fun provideAuthInfoMutableProvider(
        manager: AuthInfoDataStoreManager
    ): AuthInfoMutableProvider {
        return manager
    }

    @Provides
    @Singleton
    fun provideAuthInfoProvider(
        manager: AuthInfoDataStoreManager
    ): AuthInfoProvider {
        return manager
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authProvider: AuthInfoMutableProvider
    ): AuthRepository {
        return AuthRepositoryImpl(authProvider)
    }

    @Provides
    @Singleton
    fun provideTodoItemsRepository(
        service: TasksService,
        dao: TaskDao,
        authEditor: AuthInfoMutableProvider
    ): TodoItemsRepository {
        return TasksRepositoryImpl(authEditor, service, dao)
    }
}