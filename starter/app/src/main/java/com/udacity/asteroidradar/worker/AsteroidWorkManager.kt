package com.udacity.asteroidradar.worker

import android.app.Application
import androidx.work.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidWorkManager : Application() {

    private val applicationScope= CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        startWorkers()
    }


    private fun startWorkers(){
        applicationScope.launch {
            setupWorkerInBackground()
        }
    }

    private fun setupWorkerInBackground() {
        val constraint = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<AsteroidWorker>(
            1,
            TimeUnit.DAYS
        ).setConstraints(constraint).build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            AsteroidWorker.ASTEROID_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }
}

