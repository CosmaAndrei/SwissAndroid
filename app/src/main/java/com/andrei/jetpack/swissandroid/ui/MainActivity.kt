package com.andrei.jetpack.swissandroid.ui

import android.os.Bundle
import com.andrei.jetpack.swissandroid.R
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    companion object {
        private const val TAG = "DaggerActivity"
    }

    @Inject
    lateinit var someString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
