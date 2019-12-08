package com.andrei.jetpack.swissandroid.di.mainactivityscope

import com.andrei.jetpack.swissandroid.network.ProductApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
object ProductApiModule {

    @MainActivityScope
    @JvmStatic
    @Provides
    fun provideProductApi(retrofit: Retrofit): ProductApi = retrofit.create(ProductApi::class.java)

}