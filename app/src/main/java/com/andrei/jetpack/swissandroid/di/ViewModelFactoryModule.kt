package com.andrei.jetpack.swissandroid.di

import androidx.lifecycle.ViewModelProvider
import com.andrei.jetpack.swissandroid.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module
abstract class ViewModelFactoryModule {
    // Creates an instance of our template view model factory
    @Singleton
    @Binds
    abstract fun bindViewModelFactory(viewModelProviderFactory: ViewModelProviderFactory): ViewModelProvider.Factory

}