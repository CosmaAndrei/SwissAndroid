package com.andrei.jetpack.swissandroid.di

import android.app.Application
import com.andrei.jetpack.swissandroid.BaseApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/*
* In this class we need to add all the @Module classes we build.
*
* The app component owns the singleton scope.*/
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuildersModule::class,
        FragmentBuildersModule::class,
        AppModule::class,
        ViewModelFactoryModule::class,
        NetworkModule::class,
        PersistenceModule::class
    ]
)
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}