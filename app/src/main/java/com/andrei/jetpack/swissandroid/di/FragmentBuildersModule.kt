package com.andrei.jetpack.swissandroid.di

import com.andrei.jetpack.swissandroid.ui.main.GradesFragment
import com.andrei.jetpack.swissandroid.ui.main.LevelOneFragment
import com.andrei.jetpack.swissandroid.ui.main.LevelTwoFragment
import com.andrei.jetpack.swissandroid.ui.main.MainViewPagerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector(
        modules = [
            LvlOneProductsViewModelModule::class
        ]
    )
    abstract fun contributeLevelOneFragment(): LevelOneFragment

    @ContributesAndroidInjector
    abstract fun contributeLevelTwoFragment(): LevelTwoFragment

    @ContributesAndroidInjector
    abstract fun contributeGradesFragment(): GradesFragment

    @ContributesAndroidInjector
    abstract fun contributeMainViewPagerFragment(): MainViewPagerFragment
}