package com.andrei.jetpack.swissandroid.persistence.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andrei.jetpack.swissandroid.persistence.entities.Product
import com.andrei.jetpack.swissandroid.persistence.entities.ProductLvlTwo

@Dao
interface ProductLvlTwoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(product: ProductLvlTwo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(product: List<ProductLvlTwo>)

    @Query("SELECT * FROM product_lvl_two_table")
    fun getAllAsLiveData(): LiveData<List<ProductLvlTwo>>

    @Query("SELECT * FROM product_lvl_two_table")
    fun getAll(): List<ProductLvlTwo>

    @Query("DELETE FROM product_lvl_two_table")
    suspend fun deleteAll()
}