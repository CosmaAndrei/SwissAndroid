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


    @Insert(onConflict = REPLACE)
    suspend fun save(user: List<Grade>)

    @Query("SELECT * FROM grade_table")
    fun getAllAsLiveData(): LiveData<List<Grade>>


    @Query("SELECT * FROM grade_table")
    suspend fun getAll(): List<Grade>
}