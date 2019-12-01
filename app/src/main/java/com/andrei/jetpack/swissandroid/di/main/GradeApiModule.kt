package com.andrei.jetpack.swissandroid.di.main

import com.andrei.jetpack.swissandroid.network.GradeApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
object GradeApiModule {

    @MainScope
    @JvmStatic
    @Provides
    fun provideGradeApi(retrofit: Retrofit): GradeApi = retrofit.create(GradeApi::class.java)
}