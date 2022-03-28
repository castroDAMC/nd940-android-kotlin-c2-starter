package com.udacity.asteroidradar.worker

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.api.getToday
import com.udacity.asteroidradar.persistence.AsteroidsDatabase
import com.udacity.asteroidradar.persistence.AsteroidsRepository
import com.udacity.asteroidradar.persistence.getAsteroidsDataBase
import retrofit2.HttpException

class AsteroidWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {
    companion object {
        const val ASTEROID_WORK_NAME = "ReloadAsteroidsWorker"
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun doWork(): Result {
        val database = getAsteroidsDataBase(applicationContext)
        val repository = AsteroidsRepository(database)
        return try {
            repository.refreshAsteroidsList()
            repository.deleteOldAsteroids()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}