package com.udacity.asteroidradar.worker

import android.app.Application
import android.content.Context
import androidx.work.*
import androidx.work.WorkManager
import com.udacity.asteroidradar.database.NasaDatabase
import com.udacity.asteroidradar.repository.NasaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

class WorkManager: Application(){
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    private fun delayedInit() {
        applicationScope.launch {
            setupWork()
        }
    }

    private fun setupWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                setRequiresDeviceIdle(true)
            }.build()

        val repeatingRequest
                = PeriodicWorkRequestBuilder<RefreshWork>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshWork.Refresh_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest)
    }


    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }
}