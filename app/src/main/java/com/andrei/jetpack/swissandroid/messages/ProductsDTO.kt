package com.andrei.jetpack.swissandroid.messages

import com.andrei.jetpack.swissandroid.messages.ProductDTO
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProductsDTO(
    @SerializedName("products")
    @Expose
    val products: List<ProductDTO>
)