package com.salomao.data.repository

import com.google.android.gms.maps.model.LatLng
import com.salomao.data.pojo.Place
import com.salomao.data.pojo.Status
import com.salomao.data.service.PlaceService
import com.salomao.domain.provider.CoroutineContextProvider
import kotlinx.coroutines.withContext

class PlaceRepositoryImpl(
    private val contextProvider: CoroutineContextProvider,
    private val service: PlaceService
) : PlaceRepository {
    override suspend fun loadPlaceFromQuery(query: String): Status<List<Place>> =
        withContext(contextProvider.IO) {
            try {
                val response = service.getPlacesByQueryAsync(query).restaurantList?.map {
                    it.restaurantDto?.parse()!!
                }
                if (response != null) Status.Success(response)
                else Status.Error("")

            } catch (exception: Exception) {
                Status.Error(exception.message ?: "")
            }
        }

    override suspend fun loadPlaceFromLocation(latlng: LatLng): Status<List<Place>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
