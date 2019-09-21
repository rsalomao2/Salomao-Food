package com.salomao.data.dto

import com.google.gson.annotations.SerializedName
import com.salomao.data.pojo.Address
import com.salomao.data.pojo.Place

data class RestaurantDto(
    @SerializedName("name") var name: String?,
    @SerializedName("location") var locationDto: LocationDto?,
    @SerializedName("price_range") var priceRange: String?,
    @SerializedName("currency") var currency: String?,
    @SerializedName("thumb") var thumb: String?,
    @SerializedName("user_rating") var userRating: UserRatingDto?,
    @SerializedName("phone_numbers") var phone: String?
) {
    fun parse(): Place {
        return Place(
            name = name?: "",
            thumb = thumb,
            rate = userRating?.rating?.toFloat()?: 0.0f,
            currency = currency?:"",
            priceRange = priceRange?.toInt()?: 0,
            phone = phone?: "",
            address = Address(
                street = locationDto?.address?: "",
                cityName = locationDto?.city?: "",
                latitude = locationDto?.latitude?.toDouble()?: 0.0,
                longitude = locationDto?.longitude?.toDouble()?: 0.0
            )
        )
    }
}
