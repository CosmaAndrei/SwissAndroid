package com.andrei.jetpack.swissandroid.di.mainviewpagerpagescope

import androidx.lifecycle.ViewModel
import com.andrei.jetpack.swissandroid.ui.main.viewmodels.GradesViewModel
import com.andrei.jetpack.swissandroid.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/*
* This is responsible of injecting the grades view model*/
@Module
abstract class GradesViewModelModule {

    @MainViewPagerPageScope
    @Binds
    @IntoMap
    @ViewModelKey(GradesViewModel::class) // PROVIDE YOUR OWN MODELS HERE
    internal abstract fun bindGradesViewModel(gradesViewModel: GradesViewModel): ViewModel

}