package com.andrei.jetpack.swissandroid.ui.main.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.andrei.jetpack.swissandroid.persistence.entities.ProductLvlTwo
import com.andrei.jetpack.swissandroid.resource.Resource
import com.andrei.jetpack.swissandroid.ui.main.repos.MainScopeProductGradeRepo
import javax.inject.Inject

class LvlTwoProductsViewModel @Inject constructor(
    private val repo: MainScopeProductGradeRepo
) : ViewModel() {
    suspend fun refreshData() {
        repo.refreshData()
    }

    val products: LiveData<Resource<List<ProductLvlTwo>>> = repo.getLvlTwoProductsAsLiveData()
}