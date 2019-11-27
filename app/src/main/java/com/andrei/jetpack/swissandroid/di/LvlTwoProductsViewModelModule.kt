package com.andrei.jetpack.swissandroid.di

import androidx.lifecycle.ViewModel
import com.andrei.jetpack.swissandroid.ui.main.viewmodels.LvlOneProductsViewModel
import com.andrei.jetpack.swissandroid.ui.main.viewmodels.LvlTwoProductsViewModel
import com.andrei.jetpack.swissandroid.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/*
* This is responsible of injecting the lvl one view model*/
@Module
abstract class LvlTwoProductsViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LvlTwoProductsViewModel::class) // PROVIDE YOUR OWN MODELS HERE
    internal abstract fun bindLvlTwoProductsViewModel(editPlaceViewModel: LvlTwoProductsViewModel): ViewModel

}