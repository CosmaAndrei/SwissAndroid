package com.andrei.jetpack.swissandroid.persistence.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.andrei.jetpack.swissandroid.persistence.entities.Grade

@Dao
interface GradeDao {


    @Insert(onConflict = REPLACE)
    suspend fun save(user: Grade)

    @Query("SELECT * FROM grade_table")
    fun getAll(): LiveData<List<Grade>>
}