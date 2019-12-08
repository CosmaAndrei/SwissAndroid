package com.andrei.jetpack.swissandroid.di.detailscope

import androidx.lifecycle.ViewModel
import com.andrei.jetpack.swissandroid.ui.detailtwo.LvlTwoDetailViewModel
import com.andrei.jetpack.swissandroid.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class LvlTwoDetailPageViewModelModule {

    @DetailScope
    @Binds
    @IntoMap
    @ViewModelKey(LvlTwoDetailViewModel::class)
    internal abstract fun bindLvlTwoDetailPageViewModel(lvlOneDetailPageViewModel: LvlTwoDetailViewModel): ViewModel

}