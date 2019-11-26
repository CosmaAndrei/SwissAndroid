package com.andrei.jetpack.swissandroid.repos

import androidx.lifecycle.LiveData
import com.andrei.jetpack.swissandroid.network.ProductApi
import com.andrei.jetpack.swissandroid.persistence.dao.ProductDao
import com.andrei.jetpack.swissandroid.persistence.entities.Product
import com.andrei.jetpack.swissandroid.resource.*
import com.andrei.jetpack.swissandroid.util.toPersistable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductsRepo @Inject constructor(
    val productApi: ProductApi,
    val productDao: ProductDao
) {
    val name = "Level one repo working!! GOOD JOB!"

    fun getLvlOneProducts(): LiveData<Resource<List<Product>>> = object :
        NetworkBoundResource<List<Product>, List<Product>>() {
        override suspend fun saveCallResult(item: List<Product>) {
            productDao.save(item)
        }

        override fun shouldFetch(data: List<Product>?): Boolean {
            // TODO: Check if data exists or is to old.
            return true
        }

        override fun loadFromDb(): LiveData<List<Product>> {
            return productDao.getAll()
        }

        override fun createCall(): LiveData<ApiResponse<List<Product>>> {
            return object : LiveData<ApiResponse<List<Product>>>() {
                override fun onActive() {
                    super.onActive()
                    CoroutineScope(IO).launch {
                        val result: ApiResponse<List<Product>> = try {
                            val response = productApi.getLvlOneProducts()

                            if (response.isSuccessful) {
                                //Do your thing
                                ApiSuccessResponse(response.body()!!.products.toPersistable(), "")
                            } else {
                                //Handle unsuccessful response
                                ApiErrorResponse("Something went wrong.")
                            }
                        } catch (e: Exception) {
                            //Handle error
                            ApiErrorResponse("Something went wrong. $e")
                        }

                        withContext(Main) {
                            value = result
                        }
                    }
                }
            }
        }
    }.asLiveData()
}