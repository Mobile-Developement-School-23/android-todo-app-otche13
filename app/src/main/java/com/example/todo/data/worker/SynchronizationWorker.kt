package com.example.todo.data.worker

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.example.todo.R
import com.example.todo.data.abstraction.AuthInfoProvider
import com.example.todo.data.abstraction.TodoItemsRepository
import com.example.todo.utils.SYNCHRONIZATION_CHANNEL_ID
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlin.random.Random

@HiltWorker
class SynchronizationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
    private val repo: TodoItemsRepository,
    private val authProvider: AuthInfoProvider
): CoroutineWorker(context, params) {
    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(
            Random.nextInt(),
            NotificationCompat.Builder(context, SYNCHRONIZATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_sync_24dp)
                .setContentText(context.getString(R.string.synchronizing))
                .setContentTitle(context.getString(R.string.synchronization_in_progress))
                .build()
        )
    }

    override suspend fun doWork(): Result {
        if (authProvider.authInfo().token.isNotBlank())
            repo.pushTodoItems()
        return Result.success()
    }
}