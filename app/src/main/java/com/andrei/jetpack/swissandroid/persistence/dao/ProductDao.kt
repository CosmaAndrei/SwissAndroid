package com.andrei.jetpack.swissandroid.persistence.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.andrei.jetpack.swissandroid.persistence.entities.Product

@Dao
interface ProductDao {
    @Insert(onConflict = REPLACE)
    suspend fun save(product: Product)

    @Insert(onConflict = REPLACE)
    suspend fun save(product: List<Product>)

    @Query("SELECT * FROM product_table")
    fun getAllAsLiveData(): LiveData<List<Product>>

    @Query("SELECT * FROM product_table")
    suspend fun getAll(): List<Product>

    @Query("DELETE FROM product_table")
    suspend fun deleteAll()
}