package com.andrei.jetpack.swissandroid.di.mainactivityscope

import com.andrei.jetpack.swissandroid.persistence.AppDatabase
import com.andrei.jetpack.swissandroid.persistence.dao.ProductDao
import dagger.Module
import dagger.Provides

@Module
object ProductLvlOnePersistenceModule {

    @MainActivityScope
    @JvmStatic
    @Provides
    fun provideProductsDao(appDatabase: AppDatabase): ProductDao = appDatabase.productDao()

}