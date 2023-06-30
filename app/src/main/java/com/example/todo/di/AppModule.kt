package com.example.todo.di

import android.app.Application
import androidx.room.Room
import com.example.todo.data.TodoItemsApiRepositoryImpl
import com.example.todo.data.TodoItemsRepositoryImpl
import com.example.todo.data.api.TodoApiService
import com.example.todo.domain.TodoItemsRepository
import com.example.todo.data.db.TodoDatabase
import com.example.todo.domain.TodoItemsApiRepository
import com.example.todo.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

//    @Provides
//    @Singleton
//    fun provideTodoApiRepository(todoApiService: TodoApiService): TodoItemsApiRepository {
//        return TodoItemsApiRepositoryImpl(todoApiService)
//    }

    @Provides
    fun baseUrl() = BASE_URL

    @Provides
    fun logging() = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun okHttpClient() = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .header("Authorization","Bearer antiaristocrat")
                .build()
            return@addInterceptor chain.proceed(request)
        }
        .addInterceptor(logging())
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): TodoApiService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient())
            .build()
            .create(TodoApiService::class.java)
}