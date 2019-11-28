package com.andrei.jetpack.swissandroid.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.andrei.jetpack.swissandroid.persistence.AppDatabase
import com.andrei.jetpack.swissandroid.util.BASE_URL
import com.andrei.jetpack.swissandroid.util.APP_PREFERENCES
import com.andrei.jetpack.swissandroid.util.DATABASE_NAME
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/*
* Here we should put instances of object that will pe user on the entire application scope. Like retrofit, room ... etc*/
@Module
object AppModule {

    @Singleton
    @JvmStatic
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

    @Singleton
    @JvmStatic
    @Provides
    fun provideSharedPreferences(app: Application): SharedPreferences =
        app.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    @Singleton
    @JvmStatic
    @Provides
    fun provideDatabase(application: Application): AppDatabase =
        Room.databaseBuilder(
            application.applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()

    // This is a test dependency to see if dagger works
    @Singleton
    @JvmStatic
    @Provides
    fun testString(): String {
        return "Dagger WORKS!"
    }

}