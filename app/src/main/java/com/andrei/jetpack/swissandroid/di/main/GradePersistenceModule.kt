package com.andrei.jetpack.swissandroid.di.main

import com.andrei.jetpack.swissandroid.persistence.AppDatabase
import com.andrei.jetpack.swissandroid.persistence.dao.GradeDao
import dagger.Module
import dagger.Provides

@Module
object GradePersistenceModule {
    @MainScope
    @JvmStatic
    @Provides
    fun provideGradeDao(appDatabase: AppDatabase): GradeDao = appDatabase.gradeDao()
}