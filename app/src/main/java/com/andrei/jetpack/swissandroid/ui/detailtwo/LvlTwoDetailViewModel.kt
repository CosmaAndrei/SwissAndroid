package com.andrei.jetpack.swissandroid.ui.detailtwo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.andrei.jetpack.swissandroid.persistence.entities.ProductLvlTwo
import javax.inject.Inject

class LvlTwoDetailViewModel @Inject constructor(
    private val repo: LvlTwoDetailRepo
) : ViewModel() {
    fun getProductDetails(id: Int): LiveData<ProductLvlTwo> = repo.getProductAsLiveData(id)
}