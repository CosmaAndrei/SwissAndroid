package com.andrei.jetpack.swissandroid.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity( tableName = "grade_table")
data class Grade (
    @PrimaryKey
    @ColumnInfo(name = "product_id")
    val productId: String,
    @ColumnInfo(name = "client_count_level_one")
    val clientCountLevelOne: String,
    @ColumnInfo(name = "client_count_level_two")
    val clientCountLevelTwo: String,
    @ColumnInfo(name = "grade")
    val grade: Double
)