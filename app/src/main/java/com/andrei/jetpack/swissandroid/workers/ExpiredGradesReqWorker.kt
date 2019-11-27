package com.andrei.jetpack.swissandroid.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.andrei.jetpack.swissandroid.util.GRADES_REQ_EXPIRATION_TIME_KEY
import com.andrei.jetpack.swissandroid.util.APP_PREFERENCES
import java.util.*

class ExpiredGradesReqWorker(
    ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        return try {
            val preferences = applicationContext.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
            if (preferences.contains(GRADES_REQ_EXPIRATION_TIME_KEY)) {
                val expDate  = preferences.getString(GRADES_REQ_EXPIRATION_TIME_KEY, "")
                if (!expDate.equals("")) {
                    if (Date(expDate).before(Date())) {
                        with(preferences.edit()) {
                            putString(GRADES_REQ_EXPIRATION_TIME_KEY, "")
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