package com.andrei.jetpack.swissandroid.util

import com.andrei.jetpack.swissandroid.messages.ProductDTO
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