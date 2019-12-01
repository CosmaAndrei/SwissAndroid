package com.andrei.jetpack.swissandroid.di.main

import com.andrei.jetpack.swissandroid.network.ProductApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
object ProductApiModule {

    @MainScope
    @JvmStatic
    @Provides
    fun provideProductApi(retrofit: Retrofit): ProductApi = retrofit.create(ProductApi::class.java)

}