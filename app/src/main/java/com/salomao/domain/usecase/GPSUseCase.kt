package com.salomao.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.salomao.data.pojo.Location
import com.salomao.data.pojo.Status
import java.util.concurrent.TimeUnit

/*
* Class developed by Pedro a friend of my where I´ve learned a lot
* all rights for him
* */

interface GPSUseCase {
    fun observeUserLocation(updateInterval: Long = 20, timeUnit: TimeUnit = TimeUnit.SECONDS): Status<MutableLiveData<Location>>
    fun stopObservingUserLocation()
    suspend fun getUserLocation(): Status<Location>
}
