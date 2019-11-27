package com.andrei.jetpack.swissandroid.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.andrei.jetpack.swissandroid.databinding.FragmentLvlOneBinding
import com.andrei.jetpack.swissandroid.ui.main.adapters.ProductsRVAdapter
import com.andrei.jetpack.swissandroid.ui.main.viewmodels.LvlOneProductsViewModel
import com.andrei.jetpack.swissandroid.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject


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
        lvlOneProductsViewModel =
            ViewModelProviders.of(this, providerFactory).get(LvlOneProductsViewModel::class.java)

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
}