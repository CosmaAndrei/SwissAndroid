package com.andrei.jetpack.swissandroid.ui.detailone

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.andrei.jetpack.swissandroid.persistence.entities.Product
import javax.inject.Inject

class LvlOneDetailViewModel @Inject constructor(
    private val repo: LvlOneDetailRepo
) : ViewModel() {

    fun getProductDetails(id: Int): LiveData<Product> = repo.getProductAsLiveData(id)
}