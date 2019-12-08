package com.andrei.jetpack.swissandroid.di

import com.andrei.jetpack.swissandroid.di.mainactivityscope.*
import com.andrei.jetpack.swissandroid.di.mainactivityscope.MainActivityFragmentsBuildersModule
import com.andrei.jetpack.swissandroid.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/*
* In this class we must declare all activities that will use Dagger @Inject.
* In order for these to inject they must extend DaggerAppCompatActivity or DaggerActivity*/

@Module
abstract class ActivityBuildersModule {

    // Here we can define modules that are visible only in one activity
    @MainActivityScope
    @ContributesAndroidInjector(
        modules = [
            GradeApiModule::class,
            GradePersistenceModule::class,
            ProductApiModule::class,
            ProductLvlOnePersistenceModule::class,
            ProductLvlTwoPersistenceModule::class,
            MainActivityFragmentsBuildersModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity
}