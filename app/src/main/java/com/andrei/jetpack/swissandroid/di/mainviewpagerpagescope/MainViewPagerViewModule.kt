package com.andrei.jetpack.swissandroid.di.mainviewpagerpagescope

import androidx.lifecycle.ViewModel
import com.andrei.jetpack.swissandroid.ui.main.viewmodels.MainViewPagerViewModel
import com.andrei.jetpack.swissandroid.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewPagerViewModule {

    @MainViewPagerPageScope
    @Binds
    @IntoMap
    @ViewModelKey(MainViewPagerViewModel::class)
    internal abstract fun bindMainViewPagerViewModel(mainViewPagerViewModel: MainViewPagerViewModel): ViewModel

}