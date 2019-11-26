package com.andrei.jetpack.swissandroid.util

import com.andrei.jetpack.swissandroid.messages.ProductDTO
import com.andrei.jetpack.swissandroid.persistence.entities.Product
import java.util.*

fun ProductDTO.toPersistable() = Product(
    this.id,
    this.name,
    this.alias,
    this.releaseDate,
    this.clients,
    Date().apply { this.time = this.time + 60000 * 5 }
)

fun List<ProductDTO>.toPersistable() = this.map { elem -> elem.toPersistable() }