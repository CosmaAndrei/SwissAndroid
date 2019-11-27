package com.andrei.jetpack.swissandroid.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.andrei.jetpack.swissandroid.util.BASE_URL
import com.andrei.jetpack.swissandroid.util.APP_PREFERENCES
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
* Here we should put instances of object that will pe user on the entire application scope. Like retrofit, room ... etc*/
@Module
object AppModule {

    @JvmStatic
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()


    @JvmStatic
    @Provides
    fun provideSharedPreferences(app: Application): SharedPreferences =
        app.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)


    // This is a test dependency to see if dagger works
    @JvmStatic
    @Provides
    fun testString(): String {
        return "Dagger WORKS!"
    }

}