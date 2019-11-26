package com.andrei.jetpack.swissandroid.ui.main.viewmodels


import androidx.lifecycle.ViewModel
import com.andrei.jetpack.swissandroid.persistence.entities.Product

class ProductItemLvlOneViewModel(product: Product) : ViewModel() {
    val id: Int = product.uuid
    val name: String = product.name
}