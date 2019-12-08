package com.andrei.jetpack.swissandroid.di.mainactivityscope

import com.andrei.jetpack.swissandroid.persistence.AppDatabase
import com.andrei.jetpack.swissandroid.persistence.dao.ProductLvlTwoDao
import dagger.Module
import dagger.Provides

@Module
object ProductLvlTwoPersistenceModule {

    @MainActivityScope
    @JvmStatic
    @Provides
    fun provideProductLvlTwoDao(appDatabase: AppDatabase): ProductLvlTwoDao =
        appDatabase.productLvlTwoDao()
}