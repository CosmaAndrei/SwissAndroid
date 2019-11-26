package com.andrei.jetpack.swissandroid.persistence.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andrei.jetpack.swissandroid.persistence.entities.ProductLvlTwo

@Dao
interface ProductLvlTwoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(product: ProductLvlTwo)

    @Query("SELECT * FROM product_lvl_two_table")
    fun getAll(): LiveData<List<ProductLvlTwo>>
}