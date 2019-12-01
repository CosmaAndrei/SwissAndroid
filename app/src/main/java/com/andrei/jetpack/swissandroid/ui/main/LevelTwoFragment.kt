package com.andrei.jetpack.swissandroid.ui.main

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.andrei.jetpack.swissandroid.databinding.FragmentLvlTwoBinding
import com.andrei.jetpack.swissandroid.ui.main.adapters.ProductsLvlTwoRVAdapter
import com.andrei.jetpack.swissandroid.ui.main.viewmodels.LvlTwoProductsViewModel
import com.andrei.jetpack.swissandroid.util.LVL_TWO_REQ_EXPIRATION_TIME_KEY
import com.andrei.jetpack.swissandroid.util.UNIQUE_LVL_ONE_EXPIRED_PRODUCTS_WORKER
import com.andrei.jetpack.swissandroid.util.UNIQUE_LVL_TWO_EXPIRED_PRODUCTS_WORKER
import com.andrei.jetpack.swissandroid.util.isNetworkBoundResourceCacheExpired
import com.andrei.jetpack.swissandroid.viewmodel.ViewModelProviderFactory
import com.andrei.jetpack.swissandroid.workers.ExpiredLvlOneReqWorker
import com.andrei.jetpack.swissandroid.workers.ExpiredLvlTwoReqWorker
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LevelTwoFragment : DaggerFragment() {
    companion object {
        val TAG = LevelTwoFragment::class.simpleName
    }

    private lateinit var binding: FragmentLvlTwoBinding

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private lateinit var lvlTwoProductsViewModel: LvlTwoProductsViewModel

    @Inject
    lateinit var sharedPrefs: SharedPreferences

    private val listener: SharedPreferences.OnSharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
            when (key) {
                LVL_TWO_REQ_EXPIRATION_TIME_KEY -> {
                    if (prefs.isNetworkBoundResourceCacheExpired(key)) {
                        // The date was deleted because it expired
                        // Trigger a fetch and update the database. This should
                        // automatically trigger a refresh after the data is saved.
                        CoroutineScope(Dispatchers.IO).launch {
                            lvlTwoProductsViewModel.refreshData()
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
        lvlTwoProductsViewModel =
            ViewModelProviders.of(this, providerFactory).get(LvlTwoProductsViewModel::class.java)

        binding = FragmentLvlTwoBinding.inflate(inflater, container, false)
        val adapter = ProductsLvlTwoRVAdapter()
        binding.productList.adapter = adapter

        subscribeUi(adapter, binding)

        setupSwipeRefresh()

        startLvlTwoWorker()

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
    private fun subscribeUi(adapter: ProductsLvlTwoRVAdapter, binding: FragmentLvlTwoBinding) {
        lvlTwoProductsViewModel.products.observe(viewLifecycleOwner) { result ->
            Timber.d(TAG, "subscribeUi Has data: ${!result.data.isNullOrEmpty()}")
            binding.hasProducts = !result.data.isNullOrEmpty()
            adapter.submitList(result.data)
        }
    }

    // This worker checks if the lvl two products are expired
    private fun startLvlTwoWorker() {
        context?.let {
            WorkManager.getInstance(it).enqueueUniquePeriodicWork(
                UNIQUE_LVL_TWO_EXPIRED_PRODUCTS_WORKER,
                ExistingPeriodicWorkPolicy.KEEP,
                PeriodicWorkRequestBuilder<ExpiredLvlTwoReqWorker>(1, TimeUnit.MINUTES).build()
            )
        }
    }

    private fun setupSwipeRefresh() {
        with(binding.swiperefresh) {
            setOnRefreshListener {
                CoroutineScope(Dispatchers.IO).launch {
                    lvlTwoProductsViewModel.refreshData()
                    isRefreshing = false
                }
            }
        }
    }
}