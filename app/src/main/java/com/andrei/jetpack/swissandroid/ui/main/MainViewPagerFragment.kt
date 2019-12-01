package com.andrei.jetpack.swissandroid.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.andrei.jetpack.swissandroid.R
import com.andrei.jetpack.swissandroid.databinding.FragmentMainBinding
import com.andrei.jetpack.swissandroid.ui.main.adapters.GRADE_PAGE
import com.andrei.jetpack.swissandroid.ui.main.adapters.LEVEL_ONE_PRODUCTS_PAGE
import com.andrei.jetpack.swissandroid.ui.main.adapters.LEVEL_TWO_PRODUCTS_PAGE
import com.andrei.jetpack.swissandroid.ui.main.adapters.ProductsPagerAdapter
import com.andrei.jetpack.swissandroid.ui.main.listeners.IMainViewPagerFragmentListener
import com.andrei.jetpack.swissandroid.ui.main.viewmodels.LvlOneProductsViewModel
import com.andrei.jetpack.swissandroid.ui.main.viewmodels.MainViewPagerViewModel
import com.andrei.jetpack.swissandroid.viewmodel.ViewModelProviderFactory
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MainViewPagerFragment : DaggerFragment(), IMainViewPagerFragmentListener {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private lateinit var mainViewPagerViewModel: MainViewPagerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainViewPagerViewModel =
            ViewModelProviders.of(this, providerFactory).get(MainViewPagerViewModel::class.java)

        val binding = FragmentMainBinding.inflate(inflater, container, false)

        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = ProductsPagerAdapter(this, this)

        // Set the icon and text for each tab
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        return binding.root
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            LEVEL_ONE_PRODUCTS_PAGE -> getString(R.string.products_lvl_one_title)
            LEVEL_TWO_PRODUCTS_PAGE -> getString(R.string.products_lvl_two_title)
            GRADE_PAGE -> getString(R.string.grades_title)
            else -> null
        }
    }

    override suspend fun refreshAllData() {
        mainViewPagerViewModel.refreshData()
    }
}