package com.salomao.data.dto

import com.google.gson.annotations.SerializedName

data class PlaceResponseDto(
    @SerializedName("restaurants") var restaurantList: List<RestaurantItemDto>?
)
