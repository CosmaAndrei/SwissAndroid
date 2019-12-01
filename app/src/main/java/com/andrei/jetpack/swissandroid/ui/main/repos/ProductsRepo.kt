package com.andrei.jetpack.swissandroid.ui.main.repos

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.andrei.jetpack.swissandroid.network.ProductApi
import com.andrei.jetpack.swissandroid.persistence.dao.ProductDao
import com.andrei.jetpack.swissandroid.persistence.entities.Product
import com.andrei.jetpack.swissandroid.resource.*
import com.andrei.jetpack.swissandroid.util.LVL_ONE_REQ_EXPIRATION_TIME_KEY
import com.andrei.jetpack.swissandroid.util.isNetworkBoundResourceCacheExpired
import com.andrei.jetpack.swissandroid.util.setNetworkBoundResourceCacheToValid
import com.andrei.jetpack.swissandroid.util.toPersistable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class ProductsRepo @Inject constructor(
    private val productApi: ProductApi,
    private val productDao: ProductDao,
    private val prefs: SharedPreferences
) {

    fun getLvlOneProductsAsLiveData(): LiveData<Resource<List<Product>>> = object :
        NetworkBoundResource<List<Product>, List<Product>>() {
        override suspend fun saveCallResult(item: List<Product>) {
            productDao.deleteAll()
            productDao.save(item)
        }

        override fun shouldFetch(data: List<Product>?): Boolean =
            prefs.isNetworkBoundResourceCacheExpired(LVL_ONE_REQ_EXPIRATION_TIME_KEY)

        override fun loadFromDb(): LiveData<List<Product>> {
            return productDao.getAllAsLiveData()
        }

        override fun createCall(): LiveData<ApiResponse<List<Product>>> {
            return object : LiveData<ApiResponse<List<Product>>>() {
                override fun onActive() {
                    super.onActive()
                    CoroutineScope(IO).launch {
                        val result: ApiResponse<List<Product>> = fetchData()

                        withContext(Main) {
                            value = result
                        }
                    }
                }
            }
        }
    }.asLiveData()

    suspend fun refreshData() {
        withContext(IO) {
            when (val result = fetchData()) {
                is ApiSuccessResponse -> {
                    productDao.deleteAll()
                    productDao.save(result.body)
                }
            }

        }
    }

    private suspend fun fetchData(): ApiResponse<List<Product>> {
        return try {
            val response = productApi.getLvlOneProducts()

            if (response.isSuccessful) {
                // Save the date when the request was made.
                prefs.setNetworkBoundResourceCacheToValid(LVL_ONE_REQ_EXPIRATION_TIME_KEY)
                // Return the response
                ApiSuccessResponse(response.body()!!.products.toPersistable(), "")
            } else {
                //Handle unsuccessful response
                ApiErrorResponse("Something went wrong.")
            }
        } catch (e: Exception) {
            //Handle error
            ApiErrorResponse("Something went wrong. $e")
        }
    }

    suspend fun getCachedProducts(): List<Product> = productDao.getAll()
}