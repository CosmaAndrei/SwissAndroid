package com.andrei.jetpack.swissandroid.di.main

import androidx.lifecycle.ViewModel
import com.andrei.jetpack.swissandroid.ui.main.viewmodels.MainViewPagerViewModel
import com.andrei.jetpack.swissandroid.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewPagerViewModule {

    @MainScope
    @Binds
    @IntoMap
    @ViewModelKey(MainViewPagerViewModel::class)
    internal abstract fun bindMainViewPagerViewModel(mainViewPagerViewModel: MainViewPagerViewModel): ViewModel

}