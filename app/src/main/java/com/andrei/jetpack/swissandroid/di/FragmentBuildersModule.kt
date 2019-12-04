package com.andrei.jetpack.swissandroid.di

import com.andrei.jetpack.swissandroid.di.main.*
import com.andrei.jetpack.swissandroid.ui.detailone.LvlOneDetailFragment
import com.andrei.jetpack.swissandroid.ui.main.GradesFragment
import com.andrei.jetpack.swissandroid.ui.main.LevelOneFragment
import com.andrei.jetpack.swissandroid.ui.main.LevelTwoFragment
import com.andrei.jetpack.swissandroid.ui.main.MainViewPagerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @MainScope
    @ContributesAndroidInjector(
        modules = [
            LvlOneProductsViewModelModule::class,
            ProductLvlOnePersistenceModule::class,
            ProductLvlTwoPersistenceModule::class,
            GradePersistenceModule::class,
            GradeApiModule::class,
            ProductApiModule::class
        ]
    )
    abstract fun contributeLevelOneFragment(): LevelOneFragment

    @MainScope
    @ContributesAndroidInjector(
        modules = [
            LvlTwoProductsViewModelModule::class,
            LvlOneProductsViewModelModule::class,
            ProductLvlOnePersistenceModule::class,
            ProductLvlTwoPersistenceModule::class,
            GradePersistenceModule::class,
            GradeApiModule::class,
            ProductApiModule::class]
    )
    abstract fun contributeLevelTwoFragment(): LevelTwoFragment

    @MainScope
    @ContributesAndroidInjector(
        modules = [
            GradesViewModelModule::class,
            LvlOneProductsViewModelModule::class,
            ProductLvlOnePersistenceModule::class,
            ProductLvlTwoPersistenceModule::class,
            GradePersistenceModule::class,
            GradeApiModule::class,
            ProductApiModule::class]
    )
    abstract fun contributeGradesFragment(): GradesFragment

    @MainScope
    @ContributesAndroidInjector(
        modules = [
            MainViewPagerViewModule::class,
            LvlOneProductsViewModelModule::class,
            ProductLvlOnePersistenceModule::class,
            ProductLvlTwoPersistenceModule::class,
            GradePersistenceModule::class,
            GradeApiModule::class,
            ProductApiModule::class
        ]
    )
    abstract fun contributeMainViewPagerFragment(): MainViewPagerFragment

    @ContributesAndroidInjector
    abstract fun contributeLvlOneDetailFragment(): LvlOneDetailFragment
}