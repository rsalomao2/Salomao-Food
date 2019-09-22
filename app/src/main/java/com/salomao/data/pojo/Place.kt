package com.salomao.data.pojo

import java.io.Serializable

data class Place(
    val id: Int,
    val name: String,
    val thumb: String?,
    val rate: Float,
    val currency: String,
    val priceRange: Int,
    val phone: String,
    val address: Address
):Serializable
