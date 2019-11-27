package com.andrei.jetpack.swissandroid.network

import com.andrei.jetpack.swissandroid.messages.ProductsDTO
import retrofit2.Response
import retrofit2.http.GET

interface ProductApi {

    @GET("getProductsLevel1.php")
    suspend fun getLvlOneProducts(): Response<ProductsDTO>

    @GET("getProductsLevel2.php")
    suspend fun getLvlTwoProducts(): Response<ProductsDTO>

}