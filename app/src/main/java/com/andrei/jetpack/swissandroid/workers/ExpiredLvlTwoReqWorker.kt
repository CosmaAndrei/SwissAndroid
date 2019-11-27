package com.andrei.jetpack.swissandroid.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.andrei.jetpack.swissandroid.util.LVL_TWO_REQ_EXPIRATION_TIME_KEY
import com.andrei.jetpack.swissandroid.util.NETWORK_PREFERENCE
import java.util.*

class ExpiredLvlTwoReqWorker(
    ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        return try {
            val preferences = applicationContext.getSharedPreferences(NETWORK_PREFERENCE, Context.MODE_PRIVATE)
            if (preferences.contains(LVL_TWO_REQ_EXPIRATION_TIME_KEY)) {
                val expDate: Long = preferences.getLong(LVL_TWO_REQ_EXPIRATION_TIME_KEY, 0L)
                if (expDate != 0L) {
                    if (Date(expDate).before(Date())) {
                        with(preferences.edit()) {
                            putLong(LVL_TWO_REQ_EXPIRATION_TIME_KEY, 0L)
                            commit()
                        }
                    }
                }
            }
            Result.success()
        } catch (e: Exception) {
            // Log error with Timber
            Result.failure()
        }
    }

}