package com.andrei.jetpack.swissandroid.di

import com.andrei.jetpack.swissandroid.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/*
* In this class we must declare all activities that will use Dagger @Inject.
* In order for these to inject they must extend DaggerAppCompatActivity or DaggerActivity*/

@Module
abstract class ActivityBuildersModule {

    // Here we can define modules that are visible only in one activity
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}