package com.andrei.jetpack.swissandroid.di.main

import com.andrei.jetpack.swissandroid.persistence.AppDatabase
import com.andrei.jetpack.swissandroid.persistence.dao.ProductLvlTwoDao
import dagger.Module
import dagger.Provides

@Module
object ProductLvlTwoPersistenceModule {
    @MainScope
    @JvmStatic
    @Provides
    fun provideProductLvlTwoDao(appDatabase: AppDatabase): ProductLvlTwoDao =
        appDatabase.productLvlTwoDao()
}