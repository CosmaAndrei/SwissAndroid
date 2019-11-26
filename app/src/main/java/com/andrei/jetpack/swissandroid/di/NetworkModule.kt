package com.andrei.jetpack.swissandroid.di

import com.andrei.jetpack.swissandroid.network.GradeApi
import com.andrei.jetpack.swissandroid.network.ProductApi
import com.andrei.jetpack.swissandroid.util.BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
* This provides all the required network dependencies.*/
@Module
object NetworkModule {

    @JvmStatic
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

    @JvmStatic
    @Provides
    fun provideProductApi(retrofit: Retrofit): ProductApi = retrofit.create(ProductApi::class.java)

    @JvmStatic
    @Provides
    fun provideGradeApi(retrofit: Retrofit): GradeApi = retrofit.create(GradeApi::class.java)
}