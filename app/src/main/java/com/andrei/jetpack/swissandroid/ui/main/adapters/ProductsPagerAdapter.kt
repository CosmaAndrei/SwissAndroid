package com.andrei.jetpack.swissandroid.ui.main.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.andrei.jetpack.swissandroid.ui.main.GradesFragment
import com.andrei.jetpack.swissandroid.ui.main.LevelOneFragment
import com.andrei.jetpack.swissandroid.ui.main.LevelTwoFragment
import com.andrei.jetpack.swissandroid.ui.main.listeners.IMainViewPagerFragmentListener


const val LEVEL_ONE_PRODUCTS_PAGE = 0
const val LEVEL_TWO_PRODUCTS_PAGE = 1
const val GRADE_PAGE = 2

class ProductsPagerAdapter(
    listener: IMainViewPagerFragmentListener,
    fragment: Fragment
) :
    FragmentStateAdapter(fragment) {
    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */

    private val lvlOneFragment: LevelOneFragment = LevelOneFragment(listener)
    private val lvlTwoFragment: LevelTwoFragment = LevelTwoFragment(listener)
    private val gradesFragment: GradesFragment = GradesFragment(listener)

    private val tabFragmentsCreators: Map<Int, Fragment> = mapOf(
        LEVEL_ONE_PRODUCTS_PAGE to lvlOneFragment,
        LEVEL_TWO_PRODUCTS_PAGE to lvlTwoFragment,
        GRADE_PAGE to gradesFragment
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position] ?: throw IndexOutOfBoundsException()
    }
}