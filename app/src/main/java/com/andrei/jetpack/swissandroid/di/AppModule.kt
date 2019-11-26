package com.andrei.jetpack.swissandroid.di

import dagger.Module
import dagger.Provides

/*
* Here we should put instances of object that will pe user on the entire application scope. Like retrofit, room ... etc*/
@Module
object AppModule {

    // This is a test dependency to see if dagger works
    @JvmStatic
    @Provides
    fun testString(): String {
        return "Dagger WORKS!"
    }

}