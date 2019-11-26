package com.andrei.jetpack.swissandroid.ui.main.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.andrei.jetpack.swissandroid.persistence.entities.Product
import com.andrei.jetpack.swissandroid.repos.ProductsRepo
import com.andrei.jetpack.swissandroid.resource.Resource
import javax.inject.Inject

class LvlOneProductsViewModel @Inject constructor(
    val repo: ProductsRepo
) : ViewModel() {
    val hello = "HEllo"

    val products: LiveData<Resource<List<Product>>> = repo.getLvlOneProducts()
}