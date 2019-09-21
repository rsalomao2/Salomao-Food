package com.salomao.data.dto

import com.google.gson.annotations.SerializedName

class RestaurantItemDto {
    @SerializedName("restaurant") var restaurantDto: RestaurantDto? = null
}
