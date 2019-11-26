package com.andrei.jetpack.swissandroid.ui.main.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.andrei.jetpack.swissandroid.ui.main.GradesFragment
import com.andrei.jetpack.swissandroid.ui.main.LevelOneFragment
import com.andrei.jetpack.swissandroid.ui.main.LevelTwoFragment


const val LEVEL_ONE_PRODUCTS_PAGE = 0
const val LEVEL_TWO_PRODUCTS_PAGE = 1
const val GRADE_PAGE = 2

class ProductsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        LEVEL_ONE_PRODUCTS_PAGE to { LevelOneFragment() },
        LEVEL_TWO_PRODUCTS_PAGE to { LevelTwoFragment() },
        GRADE_PAGE to { GradesFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}