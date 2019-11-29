package com.andrei.jetpack.swissandroid.ui.main.repos

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.andrei.jetpack.swissandroid.network.ProductApi
import com.andrei.jetpack.swissandroid.persistence.dao.ProductLvlTwoDao
import com.andrei.jetpack.swissandroid.persistence.entities.ProductLvlTwo
import com.andrei.jetpack.swissandroid.resource.*
import com.andrei.jetpack.swissandroid.util.LVL_ONE_REQ_EXPIRATION_TIME_KEY
import com.andrei.jetpack.swissandroid.util.LVL_TWO_REQ_EXPIRATION_TIME_KEY
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

    fun getLvlTwoProducts(): LiveData<Resource<List<ProductLvlTwo>>> = object :
        NetworkBoundResource<List<ProductLvlTwo>, List<ProductLvlTwo>>() {
        override suspend fun saveCallResult(item: List<ProductLvlTwo>) {
            productDao.save(item)
        }

        override fun shouldFetch(data: List<ProductLvlTwo>?): Boolean {
            val dateString = prefs.getString(LVL_TWO_REQ_EXPIRATION_TIME_KEY, "")
            if (!dateString.equals("")) {
                return Date(dateString).before(Date())
            }
            return true
        }

        override fun loadFromDb(): LiveData<List<ProductLvlTwo>> {
            return productDao.getAll()
        }

        override fun createCall(): LiveData<ApiResponse<List<ProductLvlTwo>>> {
            return object : LiveData<ApiResponse<List<ProductLvlTwo>>>() {
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
        }
    }.asLiveData()

    suspend fun refreshData() {
        withContext(IO) {

            when (val result = fetchData()) {
                is ApiSuccessResponse -> {
                    productDao.save(result.body)
                }
            }

        }
    }

    private suspend fun fetchData(): ApiResponse<List<ProductLvlTwo>> {
        return try {
            val response = productApi.getLvlTwoProducts()

            if (response.isSuccessful) {
                // Save the date when the request was made.
                prefs.edit().putString(
                    LVL_TWO_REQ_EXPIRATION_TIME_KEY,
                    Date().apply { this.time = this.time + 60000 * 5 }.toString()
                )

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
    }
}