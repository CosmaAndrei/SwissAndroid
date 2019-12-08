package com.andrei.jetpack.swissandroid.di.mainactivityscope

import com.andrei.jetpack.swissandroid.network.GradeApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
object GradeApiModule {

    @MainActivityScope
    @JvmStatic
    @Provides
    fun provideGradeApi(retrofit: Retrofit): GradeApi = retrofit.create(GradeApi::class.java)
}