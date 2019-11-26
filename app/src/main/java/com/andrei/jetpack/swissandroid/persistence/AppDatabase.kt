package com.andrei.jetpack.swissandroid.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.andrei.jetpack.swissandroid.persistence.dao.GradeDao
import com.andrei.jetpack.swissandroid.persistence.dao.ProductDao
import com.andrei.jetpack.swissandroid.persistence.dao.ProductLvlTwoDao
import com.andrei.jetpack.swissandroid.persistence.entities.Grade
import com.andrei.jetpack.swissandroid.persistence.entities.Product
import com.andrei.jetpack.swissandroid.persistence.entities.ProductLvlTwo

@Database(entities = [Product::class, Grade::class, ProductLvlTwo::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun gradeDao(): GradeDao
    abstract fun productLvlTwoDao(): ProductLvlTwoDao
}