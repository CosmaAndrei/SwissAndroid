package com.andrei.jetpack.swissandroid.messages

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GradeDTO(
    @SerializedName("productId")
    @Expose
    val productId: String,
    @SerializedName("clientCountLevel1")
    @Expose
    val clientCountLevel1: String,
    @SerializedName("clientCountLevel2")
    @Expose
    val clientCountLevel2: String,
    @SerializedName("grade")
    @Expose
    val grade: Double
)