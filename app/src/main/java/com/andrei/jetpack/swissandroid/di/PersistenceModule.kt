package com.andrei.jetpack.swissandroid.di

import android.app.Application
import androidx.room.Room
import com.andrei.jetpack.swissandroid.persistence.AppDatabase
import com.andrei.jetpack.swissandroid.persistence.dao.GradeDao
import com.andrei.jetpack.swissandroid.persistence.dao.ProductDao
import com.andrei.jetpack.swissandroid.persistence.dao.ProductLvlTwoDao
import com.andrei.jetpack.swissandroid.util.DATABASE_NAME
import dagger.Module
import dagger.Provides

@Module
object PersistenceModule {

    @JvmStatic
    @Provides
    fun provideDatabase(application: Application): AppDatabase =
        Room.databaseBuilder(
            application.applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()

    @JvmStatic
    @Provides
    fun provideProductsDao(appDatabase: AppDatabase): ProductDao = appDatabase.productDao()

    @JvmStatic
    @Provides
    fun provideProductLvlTwoDao(appDatabase: AppDatabase): ProductLvlTwoDao =
        appDatabase.productLvlTwoDao()

    @JvmStatic
    @Provides
    fun provideGradeDao(appDatabase: AppDatabase): GradeDao = appDatabase.gradeDao()
}