package com.andrei.jetpack.swissandroid.di

import androidx.lifecycle.ViewModelProvider
import com.andrei.jetpack.swissandroid.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module


@Module
abstract class ViewModelFactoryModule {
    // Creates an instance of our template view model factory
    @Binds
    abstract fun bindViewModelFactory(viewModelProviderFactory: ViewModelProviderFactory): ViewModelProvider.Factory

}