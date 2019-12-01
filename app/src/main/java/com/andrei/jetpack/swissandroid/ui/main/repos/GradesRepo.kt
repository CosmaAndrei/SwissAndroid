package com.andrei.jetpack.swissandroid.ui.main.repos

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.andrei.jetpack.swissandroid.network.GradeApi
import com.andrei.jetpack.swissandroid.persistence.dao.GradeDao
import com.andrei.jetpack.swissandroid.persistence.entities.Grade
import com.andrei.jetpack.swissandroid.persistence.entities.Product
import com.andrei.jetpack.swissandroid.persistence.entities.ProductLvlTwo
import com.andrei.jetpack.swissandroid.resource.*
import com.andrei.jetpack.swissandroid.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class GradesRepo @Inject constructor(
    private val api: GradeApi,
    private val gradeDao: GradeDao,
    private val prefs: SharedPreferences,
    private val lvlOneRepo: ProductsRepo,
    private val lvlTwoRepo: ProductsLvlTwoRepo
) {
    fun getGrades(): LiveData<Resource<List<Grade>>> = object :
        NetworkBoundResource<List<Grade>, List<Grade>>() {
        override suspend fun saveCallResult(item: List<Grade>) {
            Timber.d("GFD Saving grades: ${item}.")
            gradeDao.deleteAll()
            gradeDao.save(item)
        }

        override fun shouldFetch(data: List<Grade>?): Boolean =
            prefs.isNetworkBoundResourceCacheExpired(GRADES_REQ_EXPIRATION_TIME_KEY)

        override fun loadFromDb(): LiveData<List<Grade>> {
            return gradeDao.getAllAsLiveData()
        }

        override fun createCall(): LiveData<ApiResponse<List<Grade>>> {
            return object : LiveData<ApiResponse<List<Grade>>>() {
                override fun onActive() {
                    super.onActive()
                    CoroutineScope(IO).launch {
                        val result: ApiResponse<List<Grade>> = fetchData()

                        withContext(Main) {
                            value = result
                        }
                    }
                }
            }
        }
    }.asLiveData()

    suspend fun refreshData() {
        Timber.d("GFD Refresh data.")
        when (val result = fetchData()) {
            is ApiSuccessResponse -> {
                gradeDao.deleteAll()
                gradeDao.save(result.body)
            }
        }
    }

    private suspend fun fetchData(): ApiResponse<List<Grade>> {
        if (prefs.isNetworkBoundResourceCacheExpired(LVL_ONE_REQ_EXPIRATION_TIME_KEY)) {
            Timber.d("GFD Refresh lvl one.")
            lvlOneRepo.refreshData()
        }

        if (prefs.isNetworkBoundResourceCacheExpired(LVL_TWO_REQ_EXPIRATION_TIME_KEY)) {
            Timber.d("GFD Refresh lvl two.")
            lvlTwoRepo.refreshData()
        }

        val lvlOneCachedProducts: List<Product> = lvlOneRepo.getCachedProducts()
        Timber.d("GFD lvl one items:${lvlOneCachedProducts}.")


        val lvlTwoCachedProducts: List<ProductLvlTwo> = lvlTwoRepo.getCachedProducts()
        Timber.d("GFD lvl two items:${lvlTwoCachedProducts}.")

        return try {
            ApiSuccessResponse(
                createRequestsParams(
                    lvlOneCachedProducts.toMutableList(),
                    lvlTwoCachedProducts.toMutableList()
                ).map {
                    api.getGrade(
                        it.second.first,
                        it.second.second,
                        it.second.third
                    ).body()!!.toPersistable(it.first)
                }.toList().also {
                    prefs.setNetworkBoundResourceCacheToValid(
                        GRADES_REQ_EXPIRATION_TIME_KEY
                    )
                }, ""
            )
        } catch (e: Exception) {
            Timber.d("GFD error: ${e}.")

            ApiErrorResponse("Something went wrong")
        }
    }

    /*
    * This returns a pair which contains the name of the item
    * and a triple which contain the requested api query params.*/
    private fun createRequestsParams(
        lvlOneProducts: MutableList<Product>,
        lvlTwoProducts: MutableList<ProductLvlTwo>
    ): List<Pair<String, Triple<Int, Int, Int>>> {
        val requests: MutableList<Pair<String, Triple<Int, Int, Int>>> = mutableListOf()

        val productsToBeDeleted = lvlOneProducts.map {
            // Find the product with the same id from lvlTwoProducts
            val mirrorProduct =
                lvlTwoProducts.find { lvlTwoProduct -> lvlTwoProduct.uuid == it.uuid }

            val mirrorProductClientsCount: Int =
                mirrorProduct?.clients?.count()
                    ?: 0
                    ?: 0

            // Create the request params
            requests.add(
                Pair(
                    it.name,
                    Triple(it.uuid, it.clients.count(), mirrorProductClientsCount)
                )
            )

            // Delete the items to avoid duplicate requests
            lvlTwoProducts.remove(mirrorProduct)
            it
        }
        lvlOneProducts.removeAll(productsToBeDeleted)

        val lvlTwoProductsToBeDeleted = lvlTwoProducts.map {
            // Find the product with the same id from lvlOneProducts
            val mirrorProduct =
                lvlOneProducts.find { lvlOneProduct -> lvlOneProduct.uuid == it.uuid }

            val mirrorProductClientsCount: Int =
                mirrorProduct?.clients?.count()
                    ?: 0
                    ?: 0

            // Create the request params
            requests.add(
                Pair(
                    it.name,
                    Triple(it.uuid, it.clients.count(), mirrorProductClientsCount)
                )
            )

            // Delete the items to avoid duplicate requests
            lvlOneProducts.remove(mirrorProduct)
            it
        }

        lvlTwoProducts.removeAll(lvlTwoProductsToBeDeleted)
        Timber.d("GFD Requests: ${requests}.")

        return requests.toList()
    }
}