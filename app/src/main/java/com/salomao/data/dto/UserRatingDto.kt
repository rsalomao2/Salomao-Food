package com.salomao.data.dto

import com.google.gson.annotations.SerializedName

data class UserRatingDto(
    @SerializedName("aggregate_rating") var rating: String?
)
