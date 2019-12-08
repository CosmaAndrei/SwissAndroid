package com.andrei.jetpack.swissandroid.di.detailscope

import androidx.lifecycle.ViewModel
import com.andrei.jetpack.swissandroid.ui.detailone.LvlOneDetailViewModel
import com.andrei.jetpack.swissandroid.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class LvlOneDetailPageViewModelModule {

    @DetailScope
    @Binds
    @IntoMap
    @ViewModelKey(LvlOneDetailViewModel::class)
    internal abstract fun bindLvlOneDetailPageViewModel(lvlOneDetailPageViewModel: LvlOneDetailViewModel): ViewModel

}