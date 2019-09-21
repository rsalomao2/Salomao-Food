package com.salomao.data.repository

import com.google.android.gms.maps.model.LatLng
import com.salomao.data.pojo.Place
import com.salomao.data.pojo.Status

interface PlaceRepository {
    suspend fun loadPlaceFromQuery(query: String): Status<List<Place>>
    suspend fun loadPlaceFromLocation(latLng: LatLng): Status<List<Place>>
}
