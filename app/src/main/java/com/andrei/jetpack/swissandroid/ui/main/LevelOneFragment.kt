package com.andrei.jetpack.swissandroid.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.andrei.jetpack.swissandroid.ui.main.adapters.ProductsRVAdapter
import com.andrei.jetpack.swissandroid.ui.main.viewmodels.LvlOneProductsViewModel
import com.andrei.jetpack.swissandroid.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.observe
import com.andrei.jetpack.swissandroid.databinding.FragmentLvlOneBinding


class LevelOneFragment : DaggerFragment() {
    companion object {
        val TAG = LevelOneFragment::class.simpleName
    }

    private lateinit var binding: FragmentLvlOneBinding

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private lateinit var lvlOneProductsViewModel: LvlOneProductsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViewModel()

        binding = FragmentLvlOneBinding.inflate(inflater, container, false)
        val adapter = ProductsRVAdapter()
        binding.productList.adapter = adapter

        subscribeUi(adapter, binding)
        return binding.root
    }

    // In this method we check the status and respond in consequence.
    private fun subscribeUi(adapter: ProductsRVAdapter, binding: FragmentLvlOneBinding) {
        lvlOneProductsViewModel.products.observe(viewLifecycleOwner) { result ->
            Log.d(TAG, "subscribeUi Has data: ${!result.data.isNullOrEmpty()}")
            binding.hasProducts = !result.data.isNullOrEmpty()
            adapter.submitList(result.data)
        }
    }

    private fun initViewModel() {
        lvlOneProductsViewModel =
            ViewModelProviders.of(this, providerFactory).get(LvlOneProductsViewModel::class.java)
        Log.d(TAG, "onCreate${lvlOneProductsViewModel.repo.name}")
        Log.d(TAG, "onCreate${lvlOneProductsViewModel.hello}")
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, "onCreate Coroutine started.")
            try {
                val response = lvlOneProductsViewModel.repo.productApi.getLvlOneProducts()
                if (response.isSuccessful) {
                    //Do your thing
                    Log.d(TAG, "onCreate Products fetched.")
                } else {
                    //Handle unsuccessful response
                    Log.d(TAG, "onCreate unsuccessful response.")

                }
            } catch (e: Exception) {
                //Handle error
                Log.d(TAG, "onCreate $e")

            }
        }

    }
}