package com.andrei.jetpack.swissandroid.di.mainactivityscope

import com.andrei.jetpack.swissandroid.persistence.AppDatabase
import com.andrei.jetpack.swissandroid.persistence.dao.GradeDao
import dagger.Module
import dagger.Provides

@Module
object GradePersistenceModule {
    @MainActivityScope
    @JvmStatic
    @Provides
    fun provideGradeDao(appDatabase: AppDatabase): GradeDao = appDatabase.gradeDao()
}