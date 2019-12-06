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
import com.andrei.jetpack.swissandroid.components.IIOErrorDialogListener
import com.andrei.jetpack.swissandroid.components.IOErrorDialog
import com.andrei.jetpack.swissandroid.databinding.FragmentLvlOneBinding
import com.andrei.jetpack.swissandroid.ui.main.adapters.ProductsRVAdapter
import com.andrei.jetpack.swissandroid.ui.main.listeners.IMainViewPagerFragmentListener
import com.andrei.jetpack.swissandroid.ui.main.viewmodels.LvlOneProductsViewModel
import com.andrei.jetpack.swissandroid.util.LVL_ONE_REQ_EXPIRATION_TIME_KEY
import com.andrei.jetpack.swissandroid.util.UNIQUE_LVL_ONE_EXPIRED_PRODUCTS_WORKER
import com.andrei.jetpack.swissandroid.util.isNetworkBoundResourceCacheExpired
import com.andrei.jetpack.swissandroid.viewmodel.ViewModelProviderFactory
import com.andrei.jetpack.swissandroid.workers.ExpiredLvlOneReqWorker
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class LevelOneFragment(listener: IMainViewPagerFragmentListener) : DaggerFragment() {
    companion object {
        val TAG = LevelOneFragment::class.simpleName
    }

    private val listener: WeakReference<IMainViewPagerFragmentListener> = WeakReference(listener)

    private val dialogId = UUID.randomUUID()

    private lateinit var binding: FragmentLvlOneBinding

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private lateinit var lvlOneProductsViewModel: LvlOneProductsViewModel

    @Inject
    lateinit var sharedPrefs: SharedPreferences

    private val spListener: SharedPreferences.OnSharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
            when (key) {
                LVL_ONE_REQ_EXPIRATION_TIME_KEY -> {
                    if (prefs.isNetworkBoundResourceCacheExpired(key)) {
                        // The date was deleted because it expired
                        // Trigger a fetch and update the database. This should
                        // automatically trigger a refresh after the data is saved.
                        CoroutineScope(IO).launch {
                            lvlOneProductsViewModel.refreshData()
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
        Timber.d("LL1!")
        lvlOneProductsViewModel =
            ViewModelProviders.of(this, providerFactory).get(LvlOneProductsViewModel::class.java)

        binding = FragmentLvlOneBinding.inflate(inflater, container, false)

        val adapter = ProductsRVAdapter()

        binding.productList.adapter = adapter

        subscribeUi(adapter, binding)

        startLvlOneWorker()

        setupSwipeRefresh()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        sharedPrefs.registerOnSharedPreferenceChangeListener(spListener)
    }

    override fun onPause() {
        super.onPause()
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(spListener)
    }

    // In this method we check the status and respond in consequence.
    private fun subscribeUi(adapter: ProductsRVAdapter, binding: FragmentLvlOneBinding) {
        lvlOneProductsViewModel.products.observe(viewLifecycleOwner) { result ->
            Timber.d(TAG, "subscribeUi Has data: ${!result.data.isNullOrEmpty()}")
            binding.hasProducts = !result.data.isNullOrEmpty()
            binding.status = result.status
            adapter.submitList(result.data)
        }
    }

    // This worker checks if the lvl one products are expired
    private fun startLvlOneWorker() {
        context?.let {
            WorkManager.getInstance(it).enqueueUniquePeriodicWork(
                UNIQUE_LVL_ONE_EXPIRED_PRODUCTS_WORKER,
                ExistingPeriodicWorkPolicy.KEEP,
                PeriodicWorkRequestBuilder<ExpiredLvlOneReqWorker>(1, TimeUnit.MINUTES).build()
            )
        }
    }

    private fun setupSwipeRefresh() {
        with(binding.swiperefresh) {
            setOnRefreshListener {
                CoroutineScope(IO).launch {
                    try {
                        listener.get()?.refreshAllData()
                    } catch (e: Exception) {
                        IOErrorDialog.NETWORK(object : IIOErrorDialogListener {
                            override fun onPositiveAction(dialogId: UUID) {
                            }

                            override fun onNegativeAction(dialogId: UUID) {
                            }
                        }, dialogId, e.message!!).show(childFragmentManager, "IOL1FragED")
                    }
                    isRefreshing = false
                }
            }
        }
    }
}