package com.andrei.jetpack.swissandroid.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.andrei.jetpack.swissandroid.util.APP_PREFERENCES
import com.andrei.jetpack.swissandroid.util.LVL_ONE_REQ_EXPIRATION_TIME_KEY
import com.andrei.jetpack.swissandroid.util.setNetworkBoundResourceCacheToExpired

class ExpiredLvlOneReqWorker(
    ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        return try {
            applicationContext.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
                .setNetworkBoundResourceCacheToExpired(
                    LVL_ONE_REQ_EXPIRATION_TIME_KEY
                )

            Result.success()
        } catch (e: Exception) {
            // Log error with Timber
            Result.failure()
        }
    }

}