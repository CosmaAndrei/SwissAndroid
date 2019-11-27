package com.andrei.jetpack.swissandroid.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.andrei.jetpack.swissandroid.util.LVL_ONE_REQ_EXPIRATION_TIME_KEY
import com.andrei.jetpack.swissandroid.util.APP_PREFERENCES
import java.util.*

class ExpiredLvlOneReqWorker(
    ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        return try {
            val preferences =
                applicationContext.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
            if (preferences.contains(LVL_ONE_REQ_EXPIRATION_TIME_KEY)) {
                val expDate = preferences.getString(LVL_ONE_REQ_EXPIRATION_TIME_KEY, "")
                if (!expDate.equals("")) {
                    if (Date(expDate).before(Date())) {
                        with(preferences.edit()) {
                            putString(LVL_ONE_REQ_EXPIRATION_TIME_KEY, "")
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