package com.andrei.jetpack.swissandroid.messages

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProductDTO(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("alias")
    @Expose
    val alias: String,
    @SerializedName("releaseDate")
    @Expose
    val releaseDate: String,
    @SerializedName("clients")
    @Expose
    val clients: List<String>
)