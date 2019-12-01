package com.andrei.jetpack.swissandroid.util

import android.content.SharedPreferences
import com.andrei.jetpack.swissandroid.messages.GradeDTO
import com.andrei.jetpack.swissandroid.messages.ProductDTO
import com.andrei.jetpack.swissandroid.persistence.entities.Grade
import com.andrei.jetpack.swissandroid.persistence.entities.Product
import com.andrei.jetpack.swissandroid.persistence.entities.ProductLvlTwo
import java.util.*

fun ProductDTO.toPersistable() = Product(
    this.id,
    this.name,
    this.alias,
    this.releaseDate,
    this.clients
)

fun List<ProductDTO>.toPersistable() = this.map { elem -> elem.toPersistable() }

fun ProductDTO.toPersistableLvlTwo() = ProductLvlTwo(
    this.id,
    this.name,
    this.alias,
    this.releaseDate,
    this.clients
)

fun List<ProductDTO>.toPersistableLvlTwo() = this.map { elem -> elem.toPersistableLvlTwo() }

fun GradeDTO.toPersistable(itemName: String) = Grade(
    this.productId,
    itemName,
    this.clientCountLevel1,
    this.clientCountLevel2,
    this.grade
)

fun SharedPreferences.isNetworkBoundResourceCacheExpired(key: String): Boolean {
    with(this.getString(key, "")) {
        if (!this.equals("")) {
            return Date(this).before(Date())
        }
    }
    return true
}

fun SharedPreferences.setNetworkBoundResourceCacheToValid(key: String) {
    with(this.edit()) {
        putString(
            key,
            Date().apply { this.time = this.time + 60000 * 5 }.toString()
        )
        commit()
    }
}

fun SharedPreferences.setNetworkBoundResourceCacheToExpired(key: String) {
    with(this) {
        if (contains(key)) {
            with(getString(key, "")) {
                // If resource is "" the cache has already been set to expired or is first run
                if (!this.equals("")) {
                    if (Date(this).before(Date())) {
                        with(edit()) {
                            putString(
                                key,
                                ""
                            )
                            commit()
                        }
                    }
                }
            }
        }
    }
}