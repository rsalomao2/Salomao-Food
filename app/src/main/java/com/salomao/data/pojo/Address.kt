package com.salomao.data.pojo

data class Address(
    val street: String,
    val cityName: String,
    val latitude: Double,
    val longitude: Double
) {
    fun getFullAddress(): String {
        return "$street \n$cityName "
    }
}
