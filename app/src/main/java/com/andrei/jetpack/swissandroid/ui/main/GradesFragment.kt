package com.andrei.jetpack.swissandroid.ui.main

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.Logger
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.andrei.jetpack.swissandroid.databinding.FragmentGradesBinding
import com.andrei.jetpack.swissandroid.ui.main.adapters.GradeRVAdapter
import com.andrei.jetpack.swissandroid.ui.main.viewmodels.GradesViewModel
import com.andrei.jetpack.swissandroid.util.GRADES_REQ_EXPIRATION_TIME_KEY
import com.andrei.jetpack.swissandroid.util.UNIQUE_EXPIRED_GRADES_WORKER
import com.andrei.jetpack.swissandroid.util.isNetworkBoundResourceCacheExpired
import com.andrei.jetpack.swissandroid.viewmodel.ViewModelProviderFactory
import com.andrei.jetpack.swissandroid.workers.ExpiredGradesReqWorker
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GradesFragment : DaggerFragment() {

    private lateinit var binding: FragmentGradesBinding

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private lateinit var gradesViewModel: GradesViewModel

    @Inject
    lateinit var sharedPrefs: SharedPreferences

    private val listener: SharedPreferences.OnSharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
            when (key) {
                GRADES_REQ_EXPIRATION_TIME_KEY -> {
                    if (prefs.isNetworkBoundResourceCacheExpired(key)) {
                        // The date was deleted because it expired
                        // Trigger a fetch and update the database. This should
                        // automatically trigger a refresh after the data is saved.
                        CoroutineScope(IO).launch {
                            gradesViewModel.refreshData()
                        }
                    }
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        gradesViewModel =
            ViewModelProviders.of(this, providerFactory).get(GradesViewModel::class.java)

        binding = FragmentGradesBinding.inflate(inflater, container, false)
        val adapter = GradeRVAdapter()
        binding.productList.adapter = adapter

        subscribeUi(adapter, binding)

        startGradesWorker()

        setupSwipeRefresh()

        Timber.d("GFD Fragment initialised.")

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        sharedPrefs.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun onPause() {
        super.onPause()
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(listener)
    }

    // In this method we check the status and respond in consequence.
    private fun subscribeUi(adapter: GradeRVAdapter, binding: FragmentGradesBinding) {
        gradesViewModel.products.observe(viewLifecycleOwner) { result ->
            Timber.d(" GFD subscribeUi Has data: ${!result.data.isNullOrEmpty()}")
            binding.hasProducts = !result.data.isNullOrEmpty()
            adapter.submitList(result.data)
        }
    }

    // This worker checks if the grades are expired
    private fun startGradesWorker() {
        context?.let {
            WorkManager.getInstance(it).enqueueUniquePeriodicWork(
                UNIQUE_EXPIRED_GRADES_WORKER,
                ExistingPeriodicWorkPolicy.KEEP,
                PeriodicWorkRequestBuilder<ExpiredGradesReqWorker>(1, TimeUnit.MINUTES).build()
            )
        }
    }

    private fun setupSwipeRefresh() {
        with(binding.swiperefresh) {
            setOnRefreshListener {
                CoroutineScope(IO).launch {
                    Timber.d("GFD Refresh data.")
                    gradesViewModel.refreshData()
                    isRefreshing = false
                }
            }
        }
    }
}