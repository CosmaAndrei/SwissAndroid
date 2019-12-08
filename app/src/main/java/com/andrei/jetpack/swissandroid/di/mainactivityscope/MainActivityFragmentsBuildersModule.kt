package com.andrei.jetpack.swissandroid.di.mainactivityscope

import com.andrei.jetpack.swissandroid.di.detailscope.DetailScope
import com.andrei.jetpack.swissandroid.di.detailscope.LvlOneDetailPageViewModelModule
import com.andrei.jetpack.swissandroid.di.detailscope.LvlTwoDetailPageViewModelModule
import com.andrei.jetpack.swissandroid.di.mainviewpagerpagescope.*
import com.andrei.jetpack.swissandroid.ui.detailone.LvlOneDetailFragment
import com.andrei.jetpack.swissandroid.ui.detailtwo.LvlTwoDetailFragment
import com.andrei.jetpack.swissandroid.ui.main.GradesFragment
import com.andrei.jetpack.swissandroid.ui.main.LevelOneFragment
import com.andrei.jetpack.swissandroid.ui.main.LevelTwoFragment
import com.andrei.jetpack.swissandroid.ui.main.MainViewPagerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityFragmentsBuildersModule {

    @MainViewPagerPageScope
    @ContributesAndroidInjector(
        modules = [
            LvlOneProductsViewModelModule::class
        ]
    )
    abstract fun contributeLevelOneFragment(): LevelOneFragment

    @MainViewPagerPageScope
    @ContributesAndroidInjector(
        modules = [
            LvlTwoProductsViewModelModule::class
        ]
    )
    abstract fun contributeLevelTwoFragment(): LevelTwoFragment

    @MainViewPagerPageScope
    @ContributesAndroidInjector(
        modules = [
            GradesViewModelModule::class
        ]
    )
    abstract fun contributeGradesFragment(): GradesFragment

    @MainViewPagerPageScope
    @ContributesAndroidInjector(
        modules = [
            MainViewPagerViewModule::class
        ]
    )
    abstract fun contributeMainViewPagerFragment(): MainViewPagerFragment

    @DetailScope
    @ContributesAndroidInjector(
        modules = [
            LvlOneDetailPageViewModelModule::class
        ]
    )
    abstract fun contributeLvlOneDetailFragment(): LvlOneDetailFragment

    @DetailScope
    @ContributesAndroidInjector(
        modules = [
            LvlTwoDetailPageViewModelModule::class

        ]
    )
    abstract fun contributeLvlTwoDetailsFragment(): LvlTwoDetailFragment
}