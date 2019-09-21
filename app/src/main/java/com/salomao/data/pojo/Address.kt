package com.salomao.data.pojo

data class Address(
    val street: String,
    val cityName: String,
    val countryName: String,
    val latitude: Long,
    val longitude: Long
) {
    fun getFullAddress(): String {
        return "$street \n$cityName "
    }
}
