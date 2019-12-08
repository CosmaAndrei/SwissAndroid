package com.andrei.jetpack.swissandroid.ui.detailone

import androidx.lifecycle.LiveData
import com.andrei.jetpack.swissandroid.persistence.dao.ProductDao
import com.andrei.jetpack.swissandroid.persistence.entities.Product
import javax.inject.Inject

class LvlOneDetailRepo @Inject constructor(
    private val productDao: ProductDao
) {
    fun getProductAsLiveData(id: Int): LiveData<Product> =
        productDao.getOne(id)
}