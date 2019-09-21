package com.salomao.data.dto

import com.google.gson.annotations.SerializedName

data class LocationDto(
    @SerializedName("address") var address: String?,
    @SerializedName("city") var city: String?,
    @SerializedName("latitude") var latitude: String?,
    @SerializedName("longitude") var longitude: String?
)
