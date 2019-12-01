package com.andrei.jetpack.swissandroid.network

import com.andrei.jetpack.swissandroid.messages.GradeDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GradeApi {
    @GET("getGrade.php")
    suspend fun getGrade(
        @Query("productId") productId: Int,
        @Query("clientCountLevel1") clientLvlOne: Int,
        @Query("clientCountLevel2") clientLvlTwo: Int
    ): Response<GradeDTO>
}