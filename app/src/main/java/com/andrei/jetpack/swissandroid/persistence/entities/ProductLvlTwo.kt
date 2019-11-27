package com.andrei.jetpack.swissandroid.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "product_lvl_two_table")
data class ProductLvlTwo (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val uuid: Int,
    @ColumnInfo( name = "name")
    val name: String,
    @ColumnInfo( name = "alias")
    val alias: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    @ColumnInfo(name = "clients")
    val clients: List<String>
)