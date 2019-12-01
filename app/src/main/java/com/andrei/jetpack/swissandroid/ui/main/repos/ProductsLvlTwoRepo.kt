package com.andrei.jetpack.swissandroid.ui.main.repos

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.andrei.jetpack.swissandroid.network.ProductApi
import com.andrei.jetpack.swissandroid.persistence.dao.ProductLvlTwoDao
import com.andrei.jetpack.swissandroid.persistence.entities.ProductLvlTwo
import com.andrei.jetpack.swissandroid.resource.*
import com.andrei.jetpack.swissandroid.util.LVL_TWO_REQ_EXPIRATION_TIME_KEY
import com.andrei.jetpack.swissandroid.util.isNetworkBoundResourceCacheExpired
import com.andrei.jetpack.swissandroid.util.setNetworkBoundResourceCacheToValid
import com.andrei.jetpack.swissandroid.util.toPersistableLvlTwo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class ProductsLvlTwoRepo @Inject constructor(
    private val productApi: ProductApi,
    private val productDao: ProductLvlTwoDao,
    private val prefs: SharedPreferences
) {

    fun getLvlTwoProductsAsLiveData(): LiveData<Resource<List<ProductLvlTwo>>> = object :
        NetworkBoundResource<List<ProductLvlTwo>, List<ProductLvlTwo>>() {
        override suspend fun saveCallResult(item: List<ProductLvlTwo>) {
            productDao.deleteAll()
            productDao.save(item)
        }

        override fun shouldFetch(data: List<ProductLvlTwo>?): Boolean =
            prefs.isNetworkBoundResourceCacheExpired(LVL_TWO_REQ_EXPIRATION_TIME_KEY)

        override fun loadFromDb(): LiveData<List<ProductLvlTwo>> =
            productDao.getAllAsLiveData()

        override fun createCall(): LiveData<ApiResponse<List<ProductLvlTwo>>> =
            object : LiveData<ApiResponse<List<ProductLvlTwo>>>() {
                override fun onActive() {
                    super.onActive()
                    CoroutineScope(IO).launch {
                        val result: ApiResponse<List<ProductLvlTwo>> = fetchData()

                        withContext(Main) {
                            value = result
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

    private suspend fun fetchData(): ApiResponse<List<ProductLvlTwo>> =
        try {
            val response = productApi.getLvlTwoProducts()

            if (response.isSuccessful) {
                // Save the date when the request was made.
                prefs.setNetworkBoundResourceCacheToValid(LVL_TWO_REQ_EXPIRATION_TIME_KEY)
                // Return the response
                ApiSuccessResponse(response.body()!!.products.toPersistableLvlTwo(), "")
            } else {
                //Handle unsuccessful response
                ApiErrorResponse("Something went wrong.")
            }
        } catch (e: Exception) {
            //Handle error
            ApiErrorResponse("Something went wrong. $e")
        }

    suspend fun getCachedProducts(): List<ProductLvlTwo> =
        productDao.getAll()
}
