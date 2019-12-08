package com.andrei.jetpack.swissandroid.ui.detailtwo

import androidx.lifecycle.LiveData
import com.andrei.jetpack.swissandroid.persistence.dao.ProductLvlTwoDao
import com.andrei.jetpack.swissandroid.persistence.entities.ProductLvlTwo
import javax.inject.Inject

class LvlTwoDetailRepo @Inject constructor(
    private val productDao: ProductLvlTwoDao
) {
    fun getProductAsLiveData(id: Int): LiveData<ProductLvlTwo> =
        productDao.getOne(id)
}