package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.todo.data.abstraction.AuthInfoProvider
import com.example.todo.data.worker.ScheduledSynchronizationWorker
import com.example.todo.data.worker.SynchronizationWorker
import com.example.todo.ui.theme.TodoAppTheme
import com.example.todo.utils.PERIODIC_SYNCHRONIZATION_WORK
import com.example.todo.utils.SYNCHRONIZATION_WORK
import com.example.todo.utils.SYNCHRONIZATION_WORK_TAG
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var authProvider: AuthInfoProvider

    private val workManager by lazy { WorkManager.getInstance(this) }
    private val syncRequest by lazy {
        OneTimeWorkRequestBuilder<SynchronizationWorker>()
            .addTag(SYNCHRONIZATION_WORK_TAG)
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupScheduledWork()

        setContent {
            TodoAppTheme {
                TodoNavigation(authProvider)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        workManager
            .beginUniqueWork(
                SYNCHRONIZATION_WORK,
                ExistingWorkPolicy.REPLACE,
                syncRequest
            )
            .enqueue()
    }

    private fun setupScheduledWork() {
        val periodicWork = PeriodicWorkRequestBuilder<ScheduledSynchronizationWorker>(
            8, TimeUnit.HOURS, 7, TimeUnit.HOURS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        workManager.enqueueUniquePeriodicWork(
            PERIODIC_SYNCHRONIZATION_WORK,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWork
        )
    }
}