package com.salomao.data.service

import com.salomao.BuildConfig.ZOMATO_API_VERSION
import com.salomao.data.dto.PlaceResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    @GET("api/$ZOMATO_API_VERSION/search")
    suspend fun getPlacesByQueryAsync(@Query("q") query: String): PlaceResponseDto
}
