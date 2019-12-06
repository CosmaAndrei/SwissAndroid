package com.andrei.jetpack.swissandroid.ui.main.repos

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.andrei.jetpack.swissandroid.messages.GradeDTO
import com.andrei.jetpack.swissandroid.messages.ProductsDTO
import com.andrei.jetpack.swissandroid.network.GradeApi
import com.andrei.jetpack.swissandroid.network.ProductApi
import com.andrei.jetpack.swissandroid.persistence.dao.GradeDao
import com.andrei.jetpack.swissandroid.persistence.dao.ProductDao
import com.andrei.jetpack.swissandroid.persistence.dao.ProductLvlTwoDao
import com.andrei.jetpack.swissandroid.persistence.entities.Grade
import com.andrei.jetpack.swissandroid.persistence.entities.Product
import com.andrei.jetpack.swissandroid.persistence.entities.ProductLvlTwo
import com.andrei.jetpack.swissandroid.resource.*
import com.andrei.jetpack.swissandroid.util.*
import kotlinx.coroutines.*
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class MainScopeProductGradeRepo @Inject constructor(
    private val prefs: SharedPreferences,
    private val gradeApi: GradeApi,
    private val productApi: ProductApi,
    private val gradeDao: GradeDao,
    private val productDao: ProductDao,
    private val productLvlTwoDao: ProductLvlTwoDao
) {
    private val futureFetchLvlOneItems = GlobalScope.async(start = CoroutineStart.LAZY) {
        fetch(
            LVL_ONE_REQ_EXPIRATION_TIME_KEY
        ) {
            productApi.getLvlOneProducts()
        }
    }

    private val futureFetchLvlTwoItems = GlobalScope.async(start = CoroutineStart.LAZY) {
        fetch(
            LVL_TWO_REQ_EXPIRATION_TIME_KEY
        ) {
            productApi.getLvlTwoProducts()
        }
    }

    private val futureItemsLvlOneSaved = GlobalScope.async(start = CoroutineStart.LAZY) { }

    private val futureItemsLvlTwoSaved = GlobalScope.async(start = CoroutineStart.LAZY) { }

    fun getLvlOneProductsAsLiveData(): LiveData<Resource<List<Product>>> = object :
        NetworkBoundResource<List<Product>, ProductsDTO>() {
        override suspend fun saveCallResult(item: ProductsDTO) {
            productDao.deleteAll()
            productDao.save(item.products.toPersistable())
            futureItemsLvlOneSaved.start()
        }

        override fun shouldFetch(data: List<Product>?): Boolean =
            prefs.isNetworkBoundResourceCacheExpired(LVL_ONE_REQ_EXPIRATION_TIME_KEY)

        override fun loadFromDb(): LiveData<List<Product>> {
            return productDao.getAllAsLiveData()
        }

        override fun createCall(): LiveData<ApiResponse<ProductsDTO>> {
            return object : LiveData<ApiResponse<ProductsDTO>>() {
                override fun onActive() {
                    super.onActive()
                    CoroutineScope(Dispatchers.IO).launch {
                        Timber.d("LL1 FETCH STARTED!")
                        futureFetchLvlOneItems.start()
                        val result = futureFetchLvlOneItems.await()

                        withContext(Dispatchers.Main) {
                            value = result
                        }
                    }
                }
            }
        }
    }.asLiveData()

    fun getLvlTwoProductsAsLiveData(): LiveData<Resource<List<ProductLvlTwo>>> = object :
        NetworkBoundResource<List<ProductLvlTwo>, ProductsDTO>() {
        override suspend fun saveCallResult(item: ProductsDTO) {
            productLvlTwoDao.deleteAll()
            productLvlTwoDao.save(item.products.toPersistableLvlTwo())
            futureItemsLvlTwoSaved.start()
        }

        override fun shouldFetch(data: List<ProductLvlTwo>?): Boolean =
            prefs.isNetworkBoundResourceCacheExpired(LVL_TWO_REQ_EXPIRATION_TIME_KEY)

        override fun loadFromDb(): LiveData<List<ProductLvlTwo>> =
            productLvlTwoDao.getAllAsLiveData()

        override fun createCall(): LiveData<ApiResponse<ProductsDTO>> =
            object : LiveData<ApiResponse<ProductsDTO>>() {
                override fun onActive() {
                    super.onActive()
                    CoroutineScope(Dispatchers.IO).launch {
                        Timber.d("LL2 FETCH STARTED!")
                        futureFetchLvlTwoItems.start()
                        val result = futureFetchLvlTwoItems.await()

                        withContext(Dispatchers.Main) {
                            value = result
                        }
                    }
                }
            }
    }.asLiveData()

    fun getGradesAsLiveData(): LiveData<Resource<List<Grade>>> = object :
        NetworkBoundResource<List<Grade>, List<Pair<String, GradeDTO>>>() {
        override suspend fun saveCallResult(item: List<Pair<String, GradeDTO>>) {
            Timber.d("GFD Saving grades: ${item}.")
            gradeDao.deleteAll()
            gradeDao.save(item.map { it.second.toPersistable(it.first) })
        }

        override fun shouldFetch(data: List<Grade>?): Boolean =
            prefs.isNetworkBoundResourceCacheExpired(GRADES_REQ_EXPIRATION_TIME_KEY)

        override fun loadFromDb(): LiveData<List<Grade>> {
            return gradeDao.getAllAsLiveData()
        }

        override fun createCall(): LiveData<ApiResponse<List<Pair<String, GradeDTO>>>> {
            return object : LiveData<ApiResponse<List<Pair<String, GradeDTO>>>>() {
                override fun onActive() {
                    super.onActive()
                    CoroutineScope(Dispatchers.IO).launch {
                        // The purpose of this delay is to give time for
                        // the first to fragments to start fetching the needed data
                        delay(1000)
                        Timber.d("LLG FETCH STARTED!")

                        val result: ApiResponse<List<Pair<String, GradeDTO>>> = fetchGrades(false)

                        withContext(Dispatchers.Main) {
                            value = result
                        }
                    }
                }
            }
        }

    }.asLiveData()


    private suspend fun <API_RESULT> fetch(
        prefsCacheExpirationDateKey: String,
        request: suspend () -> Response<API_RESULT>
    ): ApiResponse<API_RESULT> =
        try {
            val response = request()

            if (response.isSuccessful) {
                // Save the date when the request was made.
                prefs.setNetworkBoundResourceCacheToValid(prefsCacheExpirationDateKey)
                // Return the response
                ApiSuccessResponse(response.body()!!, "")
            } else {
                //Handle unsuccessful response
                ApiErrorResponse("Something went wrong.")
            }
        } catch (e: Exception) {
            //Handle error
            ApiErrorResponse("Something went wrong. $e")
        }

    // To make the query we need valid level one and two products.
    private suspend fun fetchGrades(isRefresh: Boolean): ApiResponse<List<Pair<String, GradeDTO>>> {

        futureFetchLvlOneItems.await()

        futureItemsLvlOneSaved.await()

        futureItemsLvlTwoSaved.await()

        futureItemsLvlTwoSaved.await()

        val lvlOneCachedProducts: List<Product> = productDao.getAll()
        Timber.d("GFD lvl one items:${lvlOneCachedProducts}.")


        val lvlTwoCachedProducts: List<ProductLvlTwo> = productLvlTwoDao.getAll()
        Timber.d("GFD lvl two items:${lvlTwoCachedProducts}.")

        return try {
            ApiSuccessResponse(
                createRequestsParamsForGradeQuery(
                    lvlOneCachedProducts.toMutableList(),
                    lvlTwoCachedProducts.toMutableList()
                ).map {
                    Pair(
                        it.first, gradeApi.getGrade(
                            it.second.first,
                            it.second.second,
                            it.second.third
                        ).body()!!
                    )
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
    private fun createRequestsParamsForGradeQuery(
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

    suspend fun refreshData() {
        // Fetch and update lvl one products
        when (val result = fetch(LVL_ONE_REQ_EXPIRATION_TIME_KEY) {
            productApi.getLvlOneProducts()
        }) {
            is ApiSuccessResponse -> {
                productDao.deleteAll()
                productDao.save(result.body.products.toPersistable())
                prefs.setNetworkBoundResourceCacheToValid(LVL_ONE_REQ_EXPIRATION_TIME_KEY)
            }
        }
        // Fetch and update lvl two products
        when (val result = fetch(LVL_ONE_REQ_EXPIRATION_TIME_KEY) {
            productApi.getLvlTwoProducts()
        }) {
            is ApiSuccessResponse -> {
                productLvlTwoDao.deleteAll()
                productLvlTwoDao.save(result.body.products.toPersistableLvlTwo())
                prefs.setNetworkBoundResourceCacheToValid(LVL_TWO_REQ_EXPIRATION_TIME_KEY)
            }
        }
        // Fetch and update products grades
        when (val result = fetchGrades(true)) {
            is ApiSuccessResponse -> {
                gradeDao.deleteAll()
                gradeDao.save(result.body.map { it.second.toPersistable(it.first) })
            }
        }
    }
}