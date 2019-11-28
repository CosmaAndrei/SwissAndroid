package com.andrei.jetpack.swissandroid.di

import com.andrei.jetpack.swissandroid.persistence.AppDatabase
import com.andrei.jetpack.swissandroid.persistence.dao.GradeDao
import com.andrei.jetpack.swissandroid.persistence.dao.ProductDao
import com.andrei.jetpack.swissandroid.persistence.dao.ProductLvlTwoDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object PersistenceModule {

    @Singleton
    @JvmStatic
    @Provides
    fun provideProductsDao(appDatabase: AppDatabase): ProductDao = appDatabase.productDao()

    @Singleton
    @JvmStatic
    @Provides
    fun provideProductLvlTwoDao(appDatabase: AppDatabase): ProductLvlTwoDao =
        appDatabase.productLvlTwoDao()

    @Singleton
    @JvmStatic
    @Provides
    fun provideGradeDao(appDatabase: AppDatabase): GradeDao = appDatabase.gradeDao()
}