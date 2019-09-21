package com.salomao.data.pojo

data class Place(
    val name: String,
    val thumb: String?,
    val rate: Float,
    val currency: String,
    val priceRange: Int,
    val phone: String,
    val address: Address
)
